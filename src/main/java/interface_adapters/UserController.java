package interface_adapters;
import use_cases.*;

import java.util.Scanner;

public class UserController {

    public void setUser(String type) {

    }

    /**
     * Logs in user based on email and password the user inputs into the user interface
     * @throws Exception
     */
    public void logInUser() throws Exception {
        UserData u = new UserData();
        // ask for email and password
        Scanner email = new Scanner(System.in);
        System.out.println("Enter your email: ");
        Scanner password = new Scanner(System.in);
        System.out.println("Enter your password: ");
        // call on u.login
        u.logInUser(String.valueOf(email), String.valueOf(password));
    }

    /**
     * Logs out user based on interaction between user and user interface
     */
    public void logoutUser(){
    }

    /**
     * Notifies user to change password after sending an email to the user
     */
    public void forgotPassword() {
        Scanner emailInput = new Scanner(System.in);
        System.out.println("Please enter your email address.");
        System.out.println("An email to reset your password has been sent to " + emailInput.next());
        // email leads to her instance of Entities.RegisteredUser tp change password?
    }


}
