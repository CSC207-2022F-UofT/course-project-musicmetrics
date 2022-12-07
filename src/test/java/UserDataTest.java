import entities.GuestUser;
import entities.RegisteredUser;
import entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import use_cases.UserData;

import java.io.File;
import java.util.Scanner;

public class UserDataTest {

    @Test
    public void testUserData() throws Exception {
        UserData u = new UserData();
        System.out.println(u.getUsers());

        // Test getUsers, and that there are 4 users in database
        Assertions.assertEquals(4, u.getUsers().size());

        // Test getUser
        assert u.getUser("abc@gmail.com") instanceof RegisteredUser;
        RegisteredUser user1 = (RegisteredUser) u.getUser("abc@gmail.com");
        RegisteredUser newUser = new RegisteredUser("xyz@gmail.com", "password");

        // Test checkUser: check whether user is in database
        assert u.checkUser((RegisteredUser) user1); // in database
        assert !u.checkUser(newUser); // not in database

        // Test checkStatus(user): check whether user is logged in or not
        assert !u.checkStatus((RegisteredUser) user1); // not logged in
        // catch exception that user not in database
        try {
            u.checkStatus(newUser);
        } catch (Exception e) {
            System.out.println("checkStatus case passed: user not in database");
        }

        // Test writeToTextFile
        File file1 = new File("main/java/entities/AllRegisteredUsers.txt");
        u.writeToTextFile("main/java/entities/AllRegisteredUsers.txt", "New line added.");
        Scanner checkAdded = new Scanner(file1);
        checkAdded.nextLine();
        var line = "";
        while (checkAdded.hasNextLine()) {
            line = checkAdded.nextLine();
        }
        Assertions.assertEquals(line, "New line added.");


//        // Test deleteFromTextFile
//        u.deleteFromTextFile(file1, "New line added.");
//        Scanner checkRemoved = new Scanner(file1);
//        checkRemoved.nextLine();
//        var line2 = "";
//        while (checkRemoved.hasNextLine()) {
//            line2 = checkRemoved.nextLine();
//        }
//        Assertions.assertEquals(line2, "fgh@gmail.com, password135");


        //Test saveUser
        assert u.saveUser(newUser.getEmail(), newUser.getPassword()); // new user saved successfully
        // need to check if text files have been updated again?
        assert !u.saveUser(user1.getEmail(), user1.getPassword()); // existing user saved unsuccessfully


        // Test deleteUser
        assert u.deleteUser(newUser.getEmail()); // new user deleted successfully
        assert !u.deleteUser(newUser.getEmail()); // non-existing user not deleted successfully
        assert !u.deleteUser(user1.getEmail()); // not logged in user not deleted successfully

        // Test logInUser
        u.logInUser(user1.getEmail(), user1.getPassword());
        u.isLoggedIn(); // log in successful
        try {
            u.logInUser(user1.getEmail(), user1.getPassword()); // already logged in
        } catch (Exception e) {
            System.out.println("logInUser case passed: user already logged in.");
        }
        try {
            u.logInUser("bcd@gmail.com", "notThePassword"); // wrong password
        } catch (Exception e) {
            System.out.println("logInUser case passed: user with wrong email.");
        }
        try {
            u.logInUser(newUser.getEmail(), newUser.getPassword()); //user not in database
        } catch (Exception e) {
            System.out.println("logInUser case passed: user not in database.");
    }

        // Test logOutUser
        u.logoutUser();
        assert !u.isLoggedIn(); // log out successful
        try {
            u.logoutUser(); // not logged in
        } catch (Exception e) {
            System.out.println("logOutUser case passed: user not logged in.");
        }
//        try {
//            u.logoutUser(); // user not in database
//        } catch (Exception e) {
//            System.out.println("logOutUser case passed: user not in database.");
//        }
//        try {
//            u.logoutUser(user4.getEmail(), "randomPassword"); // case: wrong password
//        } catch (Exception e) {
//            System.out.println("logOutUser case passed: wrong password.");
//        }
    }
}

