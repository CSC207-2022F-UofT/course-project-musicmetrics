package drivers;
import interface_adapters.*;
import use_cases.MusicData;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TextBasedFrontend {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to MusicMetric!");
        Searcher searcher = new Searcher();
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
                                        searcher.actionResult(actions.get(index - 1));
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
                            System.out.print("What artist you want to search? ");
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
                                        for (Map.Entry<String, Object> entry :
                                                MusicData.getArtistByName(artists.get(index - 1)).getInfo().entrySet()) {
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
                            System.out.print("What genre are you interested in? ");
                            input = scanner.nextLine();
                            List<String> genres = searcher.filterGenre(input);
                            if (genres.size() == 0) {
                                System.out.println("It seems like we don't have that genre.");
                            } else {
                                for (int i = 0; i < genres.size(); i++) {
                                    System.out.println((i + 1) + ". " + genres.get(i));
                                }
                                System.out.print("Please choose one of genres above you are interested (provide index): ");
                                try {
                                    int index = Integer.parseInt(scanner.nextLine().strip());
                                    if (1 <= index && index <= genres.size()) {
                                        List<Artist> artists = searcher.genreResult(genres.get(index - 1));
                                        for (int i = 0;i < artists.size();i++) {
                                            System.out.println((i + 1) + ". " + artists.get(i).getName());
                                        }
                                    } else {
                                        System.out.println("Invalid index");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid index");
                                }
                            }
                        }
                        else {
                            System.out.println("Invalid search type, please enter keyword, genres or artist.");
                        }
                        break;
                    case "profile":
                        System.out.println("bro");
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
