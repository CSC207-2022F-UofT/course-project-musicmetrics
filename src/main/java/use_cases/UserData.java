package use_cases;

import entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class UserData {

    // Keys are Bool values and have an arraylist of Entities.RegisteredUser objects mapped to the key.
    // ArrayList accepts User objects in case different kinds of Users are created and added in the future.
    //  True if Entities.User is logged-in currently
    //  False if Entities.User is Logged-out currently
    //  When an Entities.User logs in, their Entities.User object instance should be moved from False to True
    static HashMap<Boolean, ArrayList<User>> data = new HashMap<>();
    private User currentUser;

    public UserData() throws FileNotFoundException{
        // initialize data HashMap
        data.put(true, new ArrayList<>());
        data.put(false, new ArrayList<>());

        // initialize follows array
        FollowsBuilder f = new FollowsBuilder();

        Scanner registeredUsers = new Scanner(new File("src/main/java/entities/AllRegisteredUsers"));
        registeredUsers.useDelimiter(", ");
        registeredUsers.nextLine();
        String email2;
        String password2;
        while (registeredUsers.hasNextLine()) {
            email2 = registeredUsers.next();
            password2 = registeredUsers.next();
            RegisteredUser user2 = new RegisteredUser(email2, password2);

            data.get(false).add(user2);
        }
        registeredUsers.close();
        this.currentUser = new GuestUser();
    }

    /**
     * @param u is an Entities.User object
     * @return true/false bool telling if the user is logged in or not (True= logged in, false= not logged in)
     * or throws an exception which says that the Registered user is not in database
     */
    public boolean checkStatus(User u) throws Exception {
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
     * @return true/false boolean telling if the user is the current user
     */
    public boolean checkUser(User u) {
        return currentUser == u;
    }

    /**
     * @return an ArrayList containing all Users
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.addAll(data.get(true));
        users.addAll(data.get(false));
        return users;
    }

    /**
     * USE THIS IF you want to work with an Entities.RegisteredUser's instance object but only have their email
     *
     * @param email string representing an Entities.RegisteredUser's email
     * @return the instance object for the desired Entities.RegisteredUser
     * @throws Exception if user is not in database
     */
    public RegisteredUser getUser(String email) throws Exception {
        for (User u : getUsers()) {
            if (Objects.equals(u.toString(), email)) {
                return (RegisteredUser) u;
            }
        }
        throw new Exception("Entities.User not found");
    }

    /**
     * @param email a string representing an email for a registered user
     * @param password a string representing a password for a registered user
     */
    public boolean saveUser(String email, String password) throws Exception {
        try {
            User u = this.getUser(email);
            return false; // user already saved in system
        } catch (Exception e) {
            // User does not exist, save into HashMap
            writeToTextFile("src/main/java/entities/Follows", System.getProperty("line.separator") + email);
            writeToTextFile("src/main/java/entities/AllRegisteredUsers", ", " + email + ", " + password);

            FollowsBuilder f = new FollowsBuilder();
            RegisteredUser newUser = new RegisteredUser(email, password);
            data.get(false).add(newUser);
            logInUser(email, password);
            return true;
        }
    }

    /**
     * Deletes a user from the data HashMap
     *
     * @param email string denoting the email of user to be deleted
     * @return boolean value true/false whether user is deleted successfully
     */
    public boolean deleteUser(String email) throws Exception {
        RegisteredUser userToDelete = (RegisteredUser) this.getUser(email);
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
     * @throws Exception Already logged in, incorrect password, or email not found.
     */
    public void logInUser(String email, String password) throws Exception {
        if (checkStatus(email)) {
            throw new Exception("Already logged in");
        }
        if (!(checkStatus(email))) {
            for (User u : data.get(false)) {
                if (Objects.equals(u.toString(), email) && (u instanceof RegisteredUser)) {
                    if (Objects.equals(((RegisteredUser) u).getPassword(), password)) {
                        data.get(false).remove(u);
                        data.get(true).add(u);
                        this.currentUser = u;
                        return;
                    }
                    throw new Exception("Incorrect password");
                }
            }
        }
        throw new Exception("Email not found");
    }

    /**
     * Logs out a RegisteredUser based on their email and password
     * * @throws Exception if incorrect password, already logged out, or email not found
     */
    public void logoutUser() throws Exception {
        if (!(this.currentUser instanceof GuestUser)) {
            data.get(true).remove(this.currentUser);
            data.get(false).add(this.currentUser);
            this.currentUser = new GuestUser();
        } else {
            throw new Exception("You are not logged in. You cannot be logged out.");
        }
    }

    /**
     * Writes to a text file
     *
     * @param filename name of text file to be written to
     * @param toRemove string to be removed from the text file
     */
    public void writeToTextFile(String filename, String toRemove) throws IOException {
        File file = new File(filename);
        FileWriter fw = new FileWriter(file, true);
        fw.write(toRemove);
        fw.close();
    }

    /** Checks whether the user is logged in.
     *
     * @return a boolean whether the user is logged in or not
     */
    public boolean isLoggedIn() {
        return this.currentUser instanceof RegisteredUser;
    }

    /** Returns current user attribute
     *
     * @return currentUser attribute
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    public static void main(String[] args) throws Exception {
        UserData u = new UserData();
    }
}
