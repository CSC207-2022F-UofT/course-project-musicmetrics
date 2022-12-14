package drivers;

import interface_adapters.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TextBasedFrontend {

    public static void main(String[] args) throws FileNotFoundException {
        Searcher searcher = new Searcher(); // also sets up the MusicData
        UserDataBuilder builder;
        String email = "";
        String pwd;
        try {
            builder = new UserDataBuilder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Welcome to MusicMetric!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please type a command (type \"help\" for command list): ");
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit")) {
            if (input.length() > 0) {
                /* if a user provides an empty command (i.e., pressing enter without typing anything),
                the program will not ask user to type a valid command nor do anything. */
                switch (input.toLowerCase()) {
                    default:
                        System.out.println("Invalid command.");
                        break;
                    case "help":
                        System.out.println("help : command list");
                        System.out.println("login : log in to already existing account");
                        System.out.println("changepassword : change password");
                        System.out.println("logout : log out from logged in account");
                        System.out.println("register : register a new account");
                        System.out.println("recommend : get an artist recommendation within a specific genre");
                        System.out.println("follow : follow an artist");
                        System.out.println("followlist : see followed artists");
                        System.out.println("unfollow : unfollow an artist");
                        System.out.println("alert : get alert from followed artist (growth rate)");
                        System.out.println("search : search for data in out program");
                        System.out.println("profile : user profile");
                        System.out.println("exit : terminate the program");
                        break;
                    case "login":
                        /* Ask the user to type their email and password to log in to registered account.
                        If provided email/password does not exist or is incorrect, it notifies the user. */
                        System.out.print("Please type your email: ");
                        email = scanner.nextLine();
                        System.out.print("Please type the password: ");
                        pwd = scanner.nextLine();
                        try {
                            builder.getUserData().logInUser(email, pwd);
                            System.out.println("Successfully logged in.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "changepassword":
                        if (builder.getUserData().isLoggedIn()) {
                            System.out.print("Please type your new password: ");
                            input = scanner.next();
                            try {
                                UserController.changePassword(builder.getUserData(), email, input);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else {
                            System.out.println("You are not logged in.");
                        }
                        break;
                    case "logout":
                        /* The user can only log out when they are logged in. */
                        try {
                            builder.getUserData().logoutUser();
                            System.out.println("Successfully logged out.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "register":
                        /* Ask the user to type their email and password to log in to registered account.
                        If provided email/password does not exist or is incorrect, it notifies the user. */
                        System.out.print("Please type your email: ");
                        email = scanner.nextLine();
                        System.out.print("Please type the password: ");
                        pwd = scanner.nextLine();
                        try {
                            boolean success = builder.getUserData().saveUser(email, pwd);
                            if (success) {
                                System.out.println("Successfully registered.");
                            }
                            else {
                                System.out.println("Register failed, the email is already in use.");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "alert":
                        /* Prints out the growth rate of Artists that the user have followed. */
                        try {
                            System.out.println("Set the target growth rate for your followed artists (e.g 1.1, 1.25, 1.5): ");
                            double gr = Double.parseDouble(scanner.nextLine());
                            System.out.println("These artists have more than " + gr + "x growth within the past week:");

                            System.out.println(AlertsController.format(email, builder.getUserData(), gr));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "recommend":
                        // ask user to select a genre
                        String genre = genreAsker(scanner, searcher);
                        if (!genre.equals("Invalid index")) {
                            if (UserPresenter.checkIfGuestUser(builder.getUserData())){
                                String recNameRand = RecommendController.randomRecommend(genre, builder.getUserData());
                                Map<String, Object> infoRand = searcher.getArtistInfoByName(recNameRand);
                                System.out.println("You may be interested in the following artist:");
                                for (Map.Entry<String, Object> entry : infoRand.entrySet()) {
                                    System.out.println(entry.getKey() + ": " + entry.getValue());
                                }
                            }
                            else {
                                String recNameSim = RecommendController.similarRecommend(genre, builder.getUserData());
                                Map<String, Object> infoSimi = searcher.getArtistInfoByName(recNameSim);
                                System.out.println("You may be interested in the following artist:");
                                for (Map.Entry<String, Object> entry : infoSimi.entrySet()) {
                                    System.out.println(entry.getKey() + ": " + entry.getValue());
                                }
                            }
                        }
                        break;
                    case "follow":
                        /* Searches for Artist to follow. */
                        if (builder.getUserData().isLoggedIn()) {
                            System.out.print("Who do you want to follow? ");
                            input = scanner.nextLine();
                            List<String> artists = searcher.filterArtist(input);
                            if (artists.size() == 0) {
                                System.out.println("It seems like we don't have such artist.");
                                break;
                            }
                            for (int i = 0;i < artists.size();i++) {
                                System.out.println((i + 1) + ". " + artists.get(i));
                            }
                            System.out.print("Please choose one of artists above you want to see (provide index): ");
                            try {
                                int index = Integer.parseInt(scanner.nextLine().strip());
                                if (1 <= index && index <= artists.size()) {
                                    boolean success = UserController.followArtist(builder.getUserData(),
                                            email, artists.get(index - 1));
                                    if (success) {
                                        System.out.println("Successfully followed " + artists.get(index - 1));
                                    }
                                    else {
                                        System.out.println("You are already following that artist.");
                                    }
                                }
                                else {
                                    System.out.println("Invalid index");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid index");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else {
                            System.out.println("You are not logged in.");
                        }
                        break;
                    case "followlist":
                        /* Prints out followed artist */
                        if (builder.getUserData().isLoggedIn()) {
                            try {
                                List<String> artists = UserController.getFollowedArtistNames(builder.getUserData(), email);
                                if (artists.size() == 0) {
                                    System.out.println("You have not followed any artists.");
                                }
                                else {
                                    System.out.println("You have followed:");
                                    for (int i = 0;i < artists.size();i++) {
                                        System.out.println((i + 1) + ". " + artists.get(i));
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else {
                            System.out.println("You are not logged in.");
                        }
                        break;
                    case "unfollow":
                        /* Unfollow one artist of the user have followed */
                        if (builder.getUserData().isLoggedIn()) {
                            try {
                                List<String> artists = UserController.getFollowedArtistNames(builder.getUserData(), email);
                                if (artists.size() == 0) {
                                    System.out.println("You have not followed any artists.");
                                    break;
                                }
                                for (int i = 0;i < artists.size();i++) {
                                    System.out.println((i + 1) + ". " + artists.get(i));
                                }
                                System.out.print("Who do you want to unfollow? (provide index): ");
                                try {
                                    int index = Integer.parseInt(scanner.nextLine().strip());
                                    if (1 <= index && index <= artists.size()) {
                                        String artist = UserController.unfollowArtist(builder.getUserData(), email, artists.get(index - 1));
                                        System.out.println("Successfully unfollowed " + artist);
                                    }
                                    else {
                                        System.out.println("Invalid index");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid index");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else {
                            System.out.println("You are not logged in.");
                        }
                        break;
                    case "search":
                        /* There are 3 types of search: keyword, artist, genre.
                        Keyword search filters action that the user can take;
                        Artist search filters artists in the database, and so for genre.
                        The program prints out available option for the user based on the provided keyword.
                        Then, the user is asked to choose one of available options. */
                        System.out.print("Please choose search type (keyword, artist, genres): ");
                        input = scanner.nextLine();
                        if (input.equalsIgnoreCase("keyword")) {
                            System.out.print("What do you want to search? ");
                            input = scanner.nextLine();
                            List<String> actions = searcher.filterKeyword(input);
                            if (actions.size() == 0) {
                                System.out.println("There is no action that you can take based on the provided keyword.");
                            }
                            else {
                                for (int i = 0;i < actions.size();i++) {
                                    System.out.println((i + 1) + ". " + actions.get(i));
                                }
                                System.out.print("Please choose one of suggestions above (provide index): ");
                                try {
                                    int index = Integer.parseInt(scanner.nextLine().strip());
                                    if (1 <= index && index <= actions.size()) {
                                        List<String> artists = searcher.actionResult(actions.get(index - 1));
                                        if (artists != null) {
                                            for (int i = 0;i < artists.size();i++) {
                                                System.out.println((i + 1) + ". " + artists.get(i));
                                            }
                                        }
                                        else {
                                            System.out.println("Something went wrong, please try again.");
                                        }
                                    }
                                    else {
                                        System.out.println("Invalid index");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid index");
                                }
                            }
                        }
                        else if (input.equalsIgnoreCase("artist")) {
                            System.out.print("What artist do you want to search? ");
                            input = scanner.nextLine();
                            List<String> artists = searcher.filterArtist(input);
                            if (artists.size() == 0) {
                                System.out.println("It seems like we don't have such artist.");
                            }
                            else {
                                for (int i = 0;i < artists.size();i++) {
                                    System.out.println((i + 1) + ". " + artists.get(i));
                                }
                                System.out.print("Please choose one of artists above you want to see (provide index): ");
                                try {
                                    int index = Integer.parseInt(scanner.nextLine().strip());
                                    if (1 <= index && index <= artists.size()) {
                                        Map<String, Object> info = searcher.getArtistInfoByName(artists.get(index - 1));
                                        for (Map.Entry<String, Object> entry : info.entrySet()) {
                                            System.out.println(entry.getKey() + ": " + entry.getValue());
                                        }
                                    }
                                    else {
                                        System.out.println("Invalid index");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid index");
                                }
                            }
                        }
                        else if (input.equalsIgnoreCase("genres")) {
                            String askedGenre = genreAsker(scanner, searcher);
                            if(!askedGenre.equals("Invalid index")){
                                List<String> artists = searcher.genreResult(askedGenre);
                                for (int i = 0;i < artists.size();i++) {
                                    System.out.println((i + 1) + ". " + artists.get(i));
                                }
                            }
                        }
                        else {
                            System.out.println("Invalid search type, please enter keyword, genres or artist.");
                        }
                        break;
                    case "profile":
                        /* If the user is not yet logged in, the user is considered a GuestUser.
                        * Otherwise, considered a logged-in user (RegisteredUser). */
                        if (!builder.getUserData().isLoggedIn()) {
                            System.out.println("You are a Guest User. Type \"register\" to sign-up or type \"login\" to sign-in if you already have an account.");
                        }
                        else {
                            System.out.println("You are logged in. Email: " + email);
                        }
                        break;
                }
                System.out.print("\nPlease type a command: ");
            }
            input = scanner.nextLine();
        }
        System.out.println("Thank you for using MusicMetric.");
        System.exit(0);
    }

    /**
     * helper function that asks the user to select a genre
     *
     * @return
     */
    private static String genreAsker(Scanner scanner, Searcher searcher) {
        System.out.print("What genre are you interested in? ");
        String input = scanner.nextLine();
        List<String> genres = searcher.filterGenre(input);
        if (genres.size() == 0) {
            System.out.println("It seems like we don't have that genre.");
        }
        else {
            for (int i = 0; i < genres.size(); i++) {
                System.out.println((i + 1) + ". " + genres.get(i));
            }
            System.out.print("Please choose one of genres above you are interested (provide index): ");
            try {
                int index = Integer.parseInt(scanner.nextLine().strip());
                if (1 <= index && index <= genres.size()) {
                    return genres.get(index - 1);
                } else {
                    System.out.println("Invalid index");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid index");
            }
        }
        return "Invalid index";
    }

}