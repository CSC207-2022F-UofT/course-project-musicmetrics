package entities;

import java.util.List;
import java.util.Scanner;

public class GuestUser extends User {
    public GuestUser(){
    }

    public boolean checkUser(){
        return true;
    }

    public List<User> getUser(){
        return null;
    }

    public void register() {
        // calls on userdata to save user
    }

    public void saveUser(){
    }

    public void deleteUser(){
    }

    public void logInUser() throws Exception {
        UserData u = new UserData();
        // ask fxor email and password
        Scanner email = new Scanner(System.in);
        System.out.println("Enter your email: ");
        Scanner password = new Scanner(System.in);
        System.out.println("Enter your password: ");
        // call on u.login
        u.logInUser(String.valueOf(email), String.valueOf(password));
    }
    public void logoutUser(){
    }

    public void forgotPassword() {
        // ask for email
        Scanner emailInput = new Scanner(System.in);
        System.out.println("Please enter your email address.");
        // sends an email
        System.out.println("An email to reset your password has been sent to " + emailInput.next());
        // email leads to her instance of Entities.RegisteredUser
    }
}
