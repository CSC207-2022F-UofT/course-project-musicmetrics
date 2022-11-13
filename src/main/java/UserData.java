import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class UserData {
    private Scanner allRegisteredUsers;
    private Hashtable<String, String> allRegisteredUsersData;
    private Scanner loggedInUsers;
    private Hashtable<String, String> loggedInUsersData;
    public UserData() {
        // initialize new HashTables
        Hashtable<String, String> allRegisteredUsersData = new Hashtable<>();
        Hashtable<String, String> loggedInUsersData = new Hashtable<>();

        Scanner allRegisteredUsers = new Scanner("AllRegisteredUsers.txt");
        // map each registered user's email and password as a key value pair into HashTable
        while (this.allRegisteredUsers.hasNextLine()) {
            String line = this.allRegisteredUsers.nextLine();
            String[] temp = line.split(",");
            this.allRegisteredUsersData.put(temp[0], temp[1]);
        }

        Scanner loggedInUsers = new Scanner("LoggedInUsers.txt");
        // map each logged-in user's email and password as a key value pair into HashTable
        while (this.loggedInUsers.hasNextLine()) {
            String line = this.loggedInUsers.nextLine();
            String[] temp = line.split(",");
            this.loggedInUsersData.put(temp[0], temp[1]);
        }
    }

    public boolean checkUser() {
        return true;
    }

    private User getUsers() {
        return new GuestUser();
    }

    public void saveUser(String email, String password) throws IOException {
        // add to HashTable of Registered Users
        this.allRegisteredUsersData.put(email, password);
        // update text file
        BufferedWriter writer = new BufferedWriter(new FileWriter("AllRegisteredUsers.txt"));
        writer.write(email + ", " + password);
        writer.close();
    }

    public void deleteUser(String email, String password) throws IOException {
        this.allRegisteredUsersData.remove(email);
        // update text file - need to reread whole text file and skip that that line

    }

    // feel free to change the parameters for the login and logout functions if needed
    public void logInUser(String email, String password) throws IOException {
        // authenticate - use getEmail and getPassword in RegisteredUser?
        // update text file LoggedInUsers
        BufferedWriter writer = new BufferedWriter(new FileWriter("LoggedInUsers.txt"));
        writer.write(email + ", " + password);
        writer.close();
        // update HashTable LoggedInUsersData
        this.loggedInUsersData.remove(email);
    }

    public GuestUser logoutUser(String email, String password) {
        // update text file LoggedInUsers
        // update HashTable LoggedInUsersData
        this.loggedInUsersData.remove(email);
        // return a GuestUser instance
        return new GuestUser();
    }
}