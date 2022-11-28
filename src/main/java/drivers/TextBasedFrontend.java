package drivers;
import interface_adapters.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextBasedFrontend {

    public static void main(String[] args) throws FileNotFoundException {
        MusicData.setData();
        System.out.println("Welcome to MusicMetric!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please type a command (type \"help\" for command list): ");
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit")) {
            if (input.length() > 0) {
                switch (input.toLowerCase()) {
                    default:
                        System.out.println("Invalid command.");
                        break;
                    case "help":
                        System.out.println("help : command list");
                        System.out.println("login : log in to already existing account");
                        System.out.println("logout : log out from logged in account");
                        System.out.println("register : register a new account");
                        System.out.println("search : search for data in out program");
                        System.out.println("profile : user profile");
                        System.out.println("exit : terminate the program");
                        break;
                    case "login":
                        System.out.println("Command \"login\" is not yet supported.");
                        break;
                    case "logout":
                        System.out.println("Command \"logout\" is not yet supported.");
                        break;
                    case "register":
                        System.out.println("Command \"register\" is not yet supported.");
                        break;
                    case "search":
                        Searcher.main(null);
                        break;
                    case "profile":
                        if (UserPresenter.checkIfGuestUser()) {
                            System.out.println("You are a Guest User. Type \"register\" to sign-up or type \"login\" to sign-in if you already have an account.");
                        }
                        break;
                }
                System.out.print("Please type a command (type \"help\" for command list): ");
            }
            input = scanner.nextLine();
        }
        System.out.println("Thank you for using MusicMetric.");
        System.exit(0);
    }
}
