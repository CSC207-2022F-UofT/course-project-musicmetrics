package use_cases;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import entities.*;

public class UserData {

    // Keys are Bool values and have an arraylist of Entities.RegisteredUser objects mapped to the key.
    //  True if Entities.User is logged-in currently
    //  False if Entities.User is Logged-out currently
    //  When a Entities.User logs in, their Entities.User object instance should be moved from False to True
    static HashMap<Boolean, ArrayList<RegisteredUser>> data = new HashMap<>();
    FileWriter registeredFile = new FileWriter("AllRegisteredUsers.txt");
    FileWriter loggedInFile = new FileWriter("LoggedInUsers.txt");

    public UserData() throws Exception {
        // initialize data HashMap
        data.put(true, new ArrayList<>());
        data.put(false, new ArrayList<>());
        // read off loggedInFile and append to data
        Scanner loggedInUsers = new Scanner("LoggedInUsers.txt");
        loggedInUsers.nextLine(); // skip header
        while (loggedInUsers.hasNextLine()) {
            String line = loggedInUsers.nextLine();
            String[] temp = line.split(",");
            RegisteredUser user = new RegisteredUser(temp[0], temp[1]);
            data.get(true).add(user);
            // read off registeredUsers file and append to data
        } loggedInUsers.close();
        Scanner registeredUsers = new Scanner("AllRegisteredUsers.txt");
        registeredUsers.nextLine(); // skip header
        while (registeredUsers.hasNextLine()) {
            String line2 = registeredUsers.nextLine();
            String[] temp2 = line2.split(", ");
            // check if user is already in data - logged in
            if (!data.get(true).contains(this.getUser(temp2[0]))) {
                RegisteredUser user2 = new RegisteredUser(temp2[0], temp2[1]);
                data.get(false).add(user2);
            }
        } registeredUsers.close();
    }

    /**
     * @param u is a Entities.RegisteredUser object
     * @return true/false bool telling if the user is logged in or not (True= logged in, false= not logged in)
     * or throws an exception which says that the Registered user is not in database
     */
    public boolean checkStatus(RegisteredUser u) throws Exception {
        if (data.get(true).contains(u)) {
            return true;
        }
        if (data.get(false).contains(u)) {
            return false;
        }
        throw new Exception(u.toString() + " is not in database");
    }

    public boolean checkStatus(String email) throws Exception {
        return checkStatus(getUser(email));
    }

    /**
     * @param u Entities.RegisteredUser object
     * @return true/false boolean telling if the user exists in the database or not
     */
    public boolean checkUser(RegisteredUser u) {
        return data.containsValue(u);
    }

    /**
     * @return an ArrayList containing all Users
     */
    public ArrayList<RegisteredUser> getUsers() {
        ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();
        users.addAll(data.get(true));
        users.addAll(data.get(false));
        return users;
    }

    /**
     * USE THIS IF you want to work with a Entities.RegisteredUser's instance object but only have their email
     *
     * @param email string representing a Entities.RegisteredUser's email
     * @return the instance object for the desired Entities.RegisteredUser
     * @throws Exception if user is not in database
     */
    public RegisteredUser getUser(String email) throws Exception {
        for (RegisteredUser u : getUsers()) {
            if (Objects.equals(u.toString(), email)) {
                return u;
            }
        }
        throw new Exception("Entities.User not found");
    }

    /**
     * @param email    a string representing an email for a registered user
     * @param password a string representing a password for a registered user
     */
    public boolean saveUser(String email, String password) throws Exception {
        if (!data.get(true).contains(this.getUser(email)) && !data.get(false).contains(this.getUser(email))) {
            // create new Entities.RegisteredUser
            RegisteredUser newUser = new RegisteredUser(email, password);
            // add new Entities.RegisteredUser into data
            data.get(true).add(newUser);
            // add new Entities.RegisteredUser into AllRegisteredUsers text file
            writeToTextFile(registeredFile, email + ", " + password + "\n");
            // add new Entities.RegisteredUser into LoggedInUsers text file
            writeToTextFile(loggedInFile, email + ", " + password + "\n");
            return true;
        } return false;
    }

    public boolean deleteUser(String email, String password) throws Exception {
        RegisteredUser userToDelete = this.getUser(email);
        // check if the user is stored in data - user has to be logged-in in order to delete their account
        if (data.get(true).contains(userToDelete)) {
            data.get(true).remove(userToDelete);
            // remove Entities.RegisteredUser from AllRegisteredUsers text file
            deleteFromTextFile(registeredFile, email + ", " + password + "\n");
            // remove Entities.RegisteredUser from LoggedInUsers text file
            deleteFromTextFile(loggedInFile, email + ", " + password + "\n");
            return true;
        }
        return false;
    }

    /**
     * @param email    a string representing an email for a registered user
     * @param password a string representing a password for a registered user
     * @return the Entities.RegisteredUser instance for this email and password
     * @throws Exception Already logged in, incorrect password, or email not found.
     */
    public RegisteredUser logInUser(String email, String password) throws Exception {
        if (checkStatus(email)) {
            throw new Exception("Already logged in");
        }
        if (!(checkStatus(email))) {
            for (RegisteredUser u : data.get(false)) {
                if (Objects.equals(u.toString(), email)) {
                    if (Objects.equals(u.getPassword(), password)) {
                        data.get(false).remove(u);
                        data.get(true).add(u);
                        // add user to LoggedInUsers text file
                        writeToTextFile(loggedInFile, email + ", " + password + "\n");
                        return u;
                    }
                    throw new Exception("Incorrect password");
                }
            }
        }
        throw new Exception("Email not found");
    }

    /**
     * @param email    a string representing an email address for a registered user
     * @param password a string representing a password for a registered user
     * @return a Entities.GuestUser (if successfully logged out)
     * * @throws Exception if incorrect password, already logged out, or email not found
     */
    public GuestUser logoutUser(String email, String password) throws Exception {
        if (checkStatus(email)) {
            for (RegisteredUser u : data.get(true)) {
                if (Objects.equals(u.toString(), email)) {
                    if (Objects.equals(u.getPassword(), password)) {
                        data.get(true).remove(u);
                        data.get(false).add(u);
                        // remove user from LoggedInUsers text file
                        deleteFromTextFile(loggedInFile, email + ", " + password + "\n");
                        return new GuestUser();
                    }
                    throw new Exception("Incorrect password");
                }
            }
        }
        if (!(checkStatus(email))) {
            throw new Exception("Already logged out");
        }
        throw new Exception("Email not found");
    }

    /**
     * @param filename a File that needs to be updated
     * @param toAdd    a string that is to be written onto the file
     *                 this method write a string onto the text file
     */
    public void writeToTextFile(FileWriter filename, String toAdd) throws IOException {
        filename.write(toAdd);
        filename.close();
    }

    /**
     * @param filename a File that needs to be updated
     * @param toRemove a string that is to be removed from the file
     *                 this method removes a string from the text file
     */
    public void deleteFromTextFile(FileWriter filename, String toRemove) throws IOException {
        Scanner scanned = new Scanner((Readable) filename);
        StringBuilder content = new StringBuilder();
        while (scanned.hasNextLine()) {
            // read and store line temporarily if it doesn't match the string to be removed
            if (!Objects.equals(scanned.nextLine(), toRemove)) {
                content.append(scanned.nextLine());
                content.append("\n"); // is this necessary
            }
            // update the text file by creating a new FileWriter object to replace the original FileWriter
            FileWriter foo = new FileWriter(String.valueOf(filename), false);
            foo.write(String.valueOf(content));
            filename = foo; // does filename get reassigned successfully
        } scanned.close();
    }
}
