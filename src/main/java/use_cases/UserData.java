package use_cases;
import entities.GuestUser;
import entities.RegisteredUser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;


public class UserData {

    // Keys are Bool values and have an arraylist of Entities.RegisteredUser objects mapped to the key.
    //  True if Entities.User is logged-in currently
    //  False if Entities.User is Logged-out currently
    //  When a Entities.User logs in, their Entities.User object instance should be moved from False to True
    static HashMap<Boolean, ArrayList<RegisteredUser>> data = new HashMap<>();
    File registeredFile = new File("AllRegisteredUsers.txt");
    File loggedInFile = new File("LoggedInUsers.txt");

    public UserData() throws Exception {
        // initialize data HashMap
        data.put(true, new ArrayList<>());
        data.put(false, new ArrayList<>());
        // read off loggedInFile and append to data
        Scanner loggedInUsers = new Scanner("LoggedInUsers.txt").useDelimiter(",");
        loggedInUsers.nextLine(); // skip header
        String email = "";
        String password = "";

        while (loggedInUsers.hasNextLine()) {
            email = loggedInUsers.next();
            password = loggedInUsers.next();
            RegisteredUser user = new RegisteredUser(email, password);
            data.get(true).add(user);
            // read off registeredUsers file and append to data
        }
        loggedInUsers.close();
        Scanner registeredUsers = new Scanner("AllRegisteredUsers.txt").useDelimiter(",");
        registeredUsers.nextLine(); // skip header
        while (registeredUsers.hasNextLine()) {
            email = registeredUsers.next();
            password = registeredUsers.next();
            // check if user is already in data - logged in
            if (!data.get(true).contains(this.getUser(email))) {
                RegisteredUser user2 = new RegisteredUser(email, password);
                data.get(false).add(user2);
            }
        }
        registeredUsers.close();
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

    /**
     * @param email is a string denoting the email of user to be checked
     * @return true/false bool telling if the user is logged in or not (True= logged in, false= not logged in)
     * or throws an exception which says that the Registered user is not in database
     */

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
            data.get(true).add(newUser);
            // add user to LoggedInUsers text file
//          writeToTextFile(loggedInFile, email + ", " + password + "\n");
            return true;
        }
        return false;
    }

    /**
     * Deletes a user from the data HashMap
     *
     * @param email string denoting the email of user to be deleted
     * @param password string denoting the password of user to be deleted
     * @return boolean value true/false whether user is deleted successfully
     */

    public boolean deleteUser(String email, String password) throws Exception {
        RegisteredUser userToDelete = this.getUser(email);
        // check if the user is stored in data - user has to be logged-in in order to delete their account
        if (data.get(true).contains(userToDelete)) {
            data.get(true).remove(userToDelete);
            return true;
        }
        return false;
    }

    /**
     * Logs in a user based on email and password, returning their RegisteredUser instance if successful
     *
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
//                        writeToTextFile(loggedInFile, email + ", " + password + "\n");
                        return u;
                    }
                    throw new Exception("Incorrect password");
                }
            }
        }
        throw new Exception("Email not found");
    }

    /**
     * Logs out a RegisteredUser based on their email and password
     *
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
     * Writes to a text file
     *
     * @param filename name of text file to be written to
     * @param toRemove string to be removed from the text file
     * @throws IOException
     */

    public void writeToTextFile(String filename, String toRemove) throws IOException {
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(toRemove);
    }

//    public void deleteFromTextFile(String filename, String toRemove) throws IOException {
//        Scanner scanned = new Scanner(new File(filename));
//        StringBuilder content = new StringBuilder();
//        scanned.nextLine(); // skip header
//        while (scanned.hasNextLine()) {
//            // read and store line temporarily if it doesn't match the string to be removed
//            if (!Objects.equals(scanned.nextLine(), toRemove)) {
//                content.append(scanned.nextLine());
//                content.append("\n"); // is this necessary
//            }
//        }
//        try {
//            FileWriter foo = new FileWriter(String.valueOf(filename), false);
//            foo.write(String.valueOf(content));
//            filename = foo; // does filename get reassigned successfully
//        } catch (Exception e) {
//            // check this
//        } scanned.close();
//    }
}
