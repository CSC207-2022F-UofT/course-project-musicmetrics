import entities.GuestUser;
import entities.RegisteredUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import use_cases.UserData;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;



public class UserDataTest {

    @Test
    public void testUserData() throws Exception {
        UserData u = new UserData();
        System.out.println(u.getUsers());

        // Test getUsers, and that there are 4 users in database
        Assertions.assertEquals(4, u.getUsers().size());

        // Test getUser
        RegisteredUser user1 = u.getUser("abc@gmail.com"); // logged in
        RegisteredUser user2 = u.getUser("def@gmail.com"); // logged out
        RegisteredUser user3 = u.getUser("fgh@gmai.com"); // logged out
        RegisteredUser user4 = u.getUser("bcd@gmail.com"); // logged in
        RegisteredUser newUser = new RegisteredUser("xyz@gmail.com", "password");

        // Test checkUser: check whether user is in database
        assert u.checkUser(user1); // in data and logged-in
        assert u.checkUser(user2); // in data and not logged in
        assert !u.checkUser(newUser); // not in database

        // Test checkStatus(user): check whether user is logged in, logged out, or does not exist in database
        assert u.checkStatus(user1); // logged in
        assert !u.checkStatus(user2); // not logged in
        // catch exception that user not in database
        try {
            u.checkStatus(newUser);
        } catch (Exception e) {
            System.out.println("checkStatus case passed: user not in database");
        }

        // Test writeToTextFile
        File file1 = new File("main/AllRegisteredUsers.txt");
        u.writeToTextFile(file1, "New line added.");
        Scanner checkAdded = new Scanner(file1);
        checkAdded.nextLine();
        var line = "";
        while (checkAdded.hasNextLine()) {
            line = checkAdded.nextLine();
        }
        Assertions.assertEquals(line, "New line added.");


        // Test deleteFromTextFile
        u.deleteFromTextFile(file1, "New line added.");
        Scanner checkRemoved = new Scanner(file1);
        checkRemoved.nextLine();
        var line2 = "";
        while (checkRemoved.hasNextLine()) {
            line2 = checkRemoved.nextLine();
        }
        Assertions.assertEquals(line2, "fgh@gmail.com, password135");


        //Test saveUser
        assert u.saveUser(newUser.getEmail(), newUser.getPassword()); // new user saved successfully
        // need to check  if text files have been updated again?
        assert !u.saveUser(user1.getEmail(), user1.getPassword()); // existing user saved unsuccessfully


        // Test deleteUser
        assert u.deleteUser(newUser.getEmail(), newUser.getPassword()); // new user deleted successfully
        assert !u.deleteUser(newUser.getEmail(), newUser.getPassword()); // non-existing user not deleted successfully
        assert !u.deleteUser(user2.getEmail(), user2.getPassword()); // not logged in user not deleted successfully

        // Test logInUser
        assert u.logInUser(user2.getEmail(), user2.getPassword()) == u.getUser(user2.getEmail()); // log in successful
        try {
            u.logInUser(user1.getEmail(), user1.getPassword()); // already logged in
        } catch (Exception e) {
            System.out.println("logInUser case passed: user already logged in.");
        }
        try {
            u.logInUser(user3.getEmail(), "notThePassword"); // wrong password
        } catch (Exception e) {
            System.out.println("logInUser case passed: user with wrong email.");
        }
        try {
            u.logInUser(newUser.getEmail(), newUser.getPassword()); //user not in database
        } catch (Exception e) {
            System.out.println("logInUser case passed: user not in database.");
    }

        // Test logOutUser
        assert u.logoutUser(user1.getEmail(), user1.getPassword()) instanceof GuestUser; // log out successful
        try {
            u.logoutUser(user1.getEmail(), user1.getPassword()); // not logged in
        } catch (Exception e) {
            System.out.println("logOutUser case passed: user not logged in.");
        }
        try {
            u.logoutUser(newUser.getEmail(), newUser.getPassword()); // user not in database
        } catch (Exception e) {
            System.out.println("logOutUser case passed: user not in database.");
        }
        try {
            u.logoutUser(user4.getEmail(), "randomPassword"); // case: wrong password
        } catch (Exception e) {
            System.out.println("logOutUser case passed: wrong password.");
        }
    }
}

