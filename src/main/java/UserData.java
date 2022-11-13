import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

public class UserData {

    // Keys are Bool values and have an arraylist of RegisteredUser objects mapped to the key.
    //  True if User is logged-in currently
    //  False if User is Logged-out currently
    //  When a User logs in, their User object instance should be moved from False to True
    static HashMap<Boolean, ArrayList<RegisteredUser>> data = new HashMap<>();

    public UserData() {
    }

    /**
     * @param u is a RegisteredUser object
     * @return true/false bool telling if the user is logged in or not (True= logged in, false= not logged in)
     *      or throws an exception which says that the Registered user is not in database
     */
    public boolean checkStatus(RegisteredUser u) throws Exception{
        if (data.get(true).contains(u)) { return true; };
        if (data.get(false).contains(u)) { return false; };
        throw new Exception(u.toString() + " is not in database");
    }

    public boolean checkStatus(String email) throws Exception {
        return checkStatus(getUser(email));
    }

    public boolean checkUser() {
        return true;
    }

    /**
     *
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
    private RegisteredUser getUser(String email) throws Exception {
        for (RegisteredUser u: getUsers()) {
            if (u.toString() == email) { return u; };
        }
        throw new Exception("User not found");
    }


    public void saveUser(String email, String password) throws IOException {
        // add to HashTable of Registered Users
        this.allRegisteredUsersData.put(email, password);
        // update text file
        BufferedWriter writer = new BufferedWriter(new FileWriter("AllRegisteredUsers.txt"));
        writer.write(email + ", " + password);
        writer.close();
    }

    public boolean deleteUser(String email, String password) throws Exception {
        if () {};
        // update text file - need to reread whole text file and skip that that line

    }

    /**
     *
     * @param email a string representing an email for a registered user
     * @param password a string representing a password for a registered user
     * @return the RegisteredUser instance for this email and password
     * @throws Exception Already logged in, incorrect password, or email not found.
     */
    public RegisteredUser logInUser(String email, String password) throws Exception {
        if (checkStatus(email)) { throw new Exception("Already logged in");}
        if (!(checkStatus(email))) {
            for (RegisteredUser u: data.get(false)) {
                if (u.toString() == email) {
                    if (u.getPassword() == password) {
                        data.get(false).remove(u);
                        data.get(true).add(u);
                        return u;
                    }
                    throw new Exception("Incorrect password");
                }
            }
        }
        throw new Exception("Email not found");
    }

    /**
     *
     * @param email a string representing an email address for a registered user
     * @param password a string representing a password for a registered user
     * @return a GuestUser (if successfully logged out)
     * @throws Exception if incorrect password, already logged out, or email not found
     */
    public GuestUser logoutUser(String email, String password) throws Exception{
        if (checkStatus(email)) {
            for (RegisteredUser u : data.get(true)) {
                if (u.toString() == email) {
                    if (u.getPassword() == password) {
                        data.get(true).remove(u);
                        data.get(false).add(u);
                        return new GuestUser();
                    };
                    throw new Exception("Incorrect password");
                }
            }
        }
        if (!(checkStatus(email))) {
            throw new Exception("Already logged out");
        }
        throw new Exception("Email not found");
        }
    }