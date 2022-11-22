import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class UserData {

    // Keys are Bool values and have an arraylist of RegisteredUser objects mapped to the key.
    //  True if User is logged-in currently
    //  False if User is Logged-out currently
    //  When a User logs in, their User object instance should be moved from False to True
    static HashMap<Boolean, ArrayList<RegisteredUser>> data = new HashMap<>();
    FileWriter registeredFile = new FileWriter("AllRegisteredUsers.txt");
    FileWriter loggedInFile = new FileWriter("LoggedInUsers.txt");

    public UserData() throws Exception {
        // initialize data HashMap
        data.put(true, new ArrayList<>());
        data.put(false, new ArrayList<>());
        // read off loggedInFile and append to data
        Scanner loggedInUsers = new Scanner("LoggedInUsers.txt");
        String line = loggedInUsers.nextLine(); // skip header
        while (loggedInUsers.hasNextLine()) {
            String[] temp = line.split(",");
            RegisteredUser user = new RegisteredUser(temp[0], temp[1]);
            data.get(true).add(user);
            // read off registeredUsers file and append to data
            loggedInUsers.nextLine();
        } loggedInUsers.close();
        Scanner registeredUsers = new Scanner("AllRegisteredUsers.txt");
        String line2 = registeredUsers.nextLine(); // skip header
        while (registeredUsers.hasNextLine()) {
            String[] temp2 = line2.split(", ");
            // check if user is already in data - logged in
            if (!data.get(true).contains(this.getUser(temp2[0]))) {
                RegisteredUser user2 = new RegisteredUser(temp2[0], temp2[1]);
                data.get(false).add(user2);
                }
            registeredUsers.nextLine();
            } registeredUsers.close();
        }

    /**
     * @param u is a RegisteredUser object
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
     * @param u RegisteredUser object
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
     * USE THIS IF you want to work with a RegisteredUser's instance object but only have their email
     *
     * @param email string representing a RegisteredUser's email
     * @return the instance object for the desired RegisteredUser
     * @throws Exception if user is not in database
     */
    public RegisteredUser getUser(String email) throws Exception {
        for (RegisteredUser u : getUsers()) {
            if (Objects.equals(u.toString(), email)) {
                return u;
            }
        }
        throw new Exception("User not found");
    }

    /**
     * @param email    a string representing an email for a registered user
     * @param password a string representing a password for a registered user
     * @return true/false boolean representing whether user is saved successfully into data
     */
    public boolean saveUser(String email, String password) throws Exception {
        // create new RegisteredUser
        if (!data.get(true).contains(this.getUser(email)) && !data.get(false).contains(this.getUser(email))) {
            RegisteredUser newUser = new RegisteredUser(email, password);
            // add new RegisteredUser into data
            data.get(true).add(newUser);
            // add new RegisteredUser into AllRegisteredUsers text file
            writeToTextFile(registeredFile, email + ", " + password + "\n");
            // add new RegisteredUser into LoggedInUsers text file
            writeToTextFile(loggedInFile, email + ", " + password + "\n");
            return true;
        }
        return false;
    }
    /**
     * @param email    a string representing an email for a registered user
     * @param password a string representing a password for a registered user
     * @return true/false boolean representing whether user is deleted successfully into data
     */
    public boolean deleteUser(String email, String password) throws Exception {
        RegisteredUser userToDelete = this.getUser(email);
        // check if the user is stored in data - user has to be logged-in in order to delete their account
        if (data.get(true).contains(userToDelete)) {
            data.get(true).remove(userToDelete);
            // remove RegisteredUser from AllRegisteredUsers text file
            deleteFromTextFile(registeredFile, email + ", " + password + "\n");
            // remove RegisteredUser from LoggedInUsers text file
            deleteFromTextFile(loggedInFile, email + ", " + password + "\n");
            return true;
        }
        return false;
    }

    /**
     * @param email    a string representing an email for a registered user
     * @param password a string representing a password for a registered user
     * @return the RegisteredUser instance for this email and password
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
     * @return a GuestUser (if successfully logged out)
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
        scanned.nextLine(); // skip header
        while (scanned.hasNextLine()) {
            // read and store line temporarily if it doesn't match the string to be removed
            if (!Objects.equals(scanned.nextLine(), toRemove)) {
                content.append(scanned.nextLine());
                content.append("\n"); // is this necessary
            }
            // update the text file by creating a new FileWriter object to replace the original FileWriter
            try {
                FileWriter foo = new FileWriter(String.valueOf(filename), false);
                foo.write(String.valueOf(content));
                filename = foo; // does filename get reassigned successfully
            } catch (Exception e) {
                System.out.println("File not updated successfully.");
            }
        } scanned.close();
    }
}