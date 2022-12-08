import entities.GuestUser;
import entities.RegisteredUser;
import entities.User;
import interface_adapters.UserDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import use_cases.UserData;

import java.io.File;
import java.util.Scanner;

public class UserDataTest {

    @Test
    public void testUserData() throws Exception {
        UserDataBuilder u = new UserDataBuilder();

        // Test getUser
        assert u.getUserData().getUser("abc@gmail.com") != null;
        RegisteredUser user1 = (RegisteredUser) u.getUserData().getUser("abc@gmail.com");
        RegisteredUser newUser = new RegisteredUser("xyz@gmail.com", "password");

        // Test checkUser: check whether user is in database
        assert u.getUserData().checkUser((RegisteredUser) user1); // in database
        assert !u.getUserData().checkUser(newUser); // not in database

        // Test checkStatus(user): check whether user is logged in or not
        assert !u.getUserData().checkStatus((RegisteredUser) user1); // not logged in
        // catch exception that user not in database
        try {
            u.getUserData().checkStatus(newUser);
        } catch (Exception e) {
            System.out.println("checkStatus case passed: user not in database");
        }

        // Test writeToTextFile
        File file1 = new File("main/java/entities/AllRegisteredUsers.txt");
        u.getUserData().writeToTextFile("main/java/entities/AllRegisteredUsers.txt", "New line added.");
        Scanner checkAdded = new Scanner(file1);
        checkAdded.nextLine();
        var line = "";
        while (checkAdded.hasNextLine()) {
            line = checkAdded.nextLine();
        }
        Assertions.assertEquals(line, "New line added.");

        //Test saveUser
        assert u.getUserData().saveUser(newUser.getEmail(), newUser.getPassword()); // new user saved successfully
        // need to check if text files have been updated again?
        assert !u.getUserData().saveUser(user1.getEmail(), user1.getPassword()); // existing user saved unsuccessfully


        // Test deleteUser
        assert u.getUserData().deleteUser(newUser.getEmail()); // new user deleted successfully
        assert !u.getUserData().deleteUser(newUser.getEmail()); // non-existing user not deleted successfully
        assert !u.getUserData().deleteUser(user1.getEmail()); // not logged in user not deleted successfully

        // Test logInUser
        u.getUserData().logInUser(user1.getEmail(), user1.getPassword());
        u.getUserData().isLoggedIn(); // log in successful
        try {
            u.getUserData().logInUser(user1.getEmail(), user1.getPassword()); // already logged in
        } catch (Exception e) {
            System.out.println("logInUser case passed: user already logged in.");
        }
        try {
            u.getUserData().logInUser("bcd@gmail.com", "notThePassword"); // wrong password
        } catch (Exception e) {
            System.out.println("logInUser case passed: user with wrong email.");
        }
        try {
            u.getUserData().logInUser(newUser.getEmail(), newUser.getPassword()); //user not in database
        } catch (Exception e) {
            System.out.println("logInUser case passed: user not in database.");
        }

        // Test logOutUser
        u.getUserData().logoutUser();
        assert !u.getUserData().isLoggedIn(); // log out successful
        try {
            u.getUserData().logoutUser(); // not logged in
        } catch (Exception e) {
            System.out.println("logOutUser case passed: user not logged in.");
        }
    }
}

