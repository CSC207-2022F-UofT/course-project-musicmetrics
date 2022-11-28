package use_cases;

import java.io.*;
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
//            writeToTextFile(registeredFile, email + ", " + password);
//            // add new Entities.RegisteredUser into LoggedInUsers text file
//            writeToTextFile(loggedInFile, email + ", " + password);
            return true;
        }
        return false;
    }

    public boolean deleteUser(String email, String password) throws Exception {
        RegisteredUser userToDelete = this.getUser(email);
        // check if the user is stored in data - user has to be logged-in in order to delete their account
        if (data.get(true).contains(userToDelete)) {
            data.get(true).remove(userToDelete);
            // remove Entities.RegisteredUser from AllRegisteredUsers text file
//            deleteFromTextFile(registeredFile, email + ", " + password + "\n");
//            // remove Entities.RegisteredUser from LoggedInUsers text file
//            deleteFromTextFile(loggedInFile, email + ", " + password + "\n");
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
//                        deleteFromTextFile(loggedInFile, email + ", " + password + "\n");
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
//    public void writeToTextFile(File filename, String toAdd) throws IOException {
//        FileWriter fw = new FileWriter(filename);
//        fw.write(toAdd);
//        fw.close();
//    }


    /**
     * @param filename a File that needs to be updated
     * @param toRemove a string that is to be removed from the file
     *                 this method removes a string from the text file
     */
//    public void deleteFromTextFile(File filename, String toRemove) throws IOException {
//        File oldFile = new File(String.valueOf(filename));
//        File newFile = new File("temp.txt"); // create new file to hold updated content
//        FileWriter newFileWriter = new FileWriter(newFile, false);
//        PrintWriter pw = new PrintWriter(newFileWriter); // to write onto the new file
//        Scanner scanOld = new Scanner(oldFile); // to read the old file
//
//        String currentLine;
//        currentLine = scanOld.nextLine(); // skip header
//        newFileWriter.write("Email, password");
//        while (scanOld.hasNextLine()) {
//            if (!Objects.equals(currentLine, toRemove)) {
//                pw.println(currentLine);
//            }
//            currentLine = scanOld.nextLine();
//        }
//        scanOld.close(); // close scanner
//        newFileWriter.close(); // close FileWriter
//        pw.close();
//        // rename old file to new file
//        filename = newFile;
//    }
}
