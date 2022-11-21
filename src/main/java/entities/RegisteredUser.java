package entities;

import interface_adapters.Alert;
import interface_adapters.Searcher;
import use_cases.UserData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class RegisteredUser extends User {
    private final String email;
    private String password;
    public ArrayList<String> follows;

    public RegisteredUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.follows = new ArrayList<>();
    }

    public Alert getAlerts(double growthRate, boolean compare, int top) {
        return null;
    }

    public void setAlerts() {

    }

    public String toString() { return this.email;}

    public void deleteAlert() {

    }

    public void updateAlert(Alert alert) {

    }

    @Override
    public boolean checkPermissions() {
        return true;
    }

    /**
     * use_cases.UserData's logout method is used
     * @return a new Entities.GuestUser instance
     * throws Exception if user is not logged in or does not exist in database
     */
    public GuestUser logout() throws Exception {
        UserData u = new UserData();
        return u.logoutUser(this.email, this.password);
    }

    /**
     * @param newPassword String that will be the new password
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * @return password String of Entities.RegisteredUser
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return email String of Entities.RegisteredUser
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param follows following ArrayList to set
     */
    public void setFollows(ArrayList<String> follows) {this.follows = follows; }

    /**
     * Adds an artist the Entities.RegisteredUser want to follow to their followings.
     */
    public void addFollow() throws FileNotFoundException {
        // Asks for the input of the user for the artist to use in the InterfaceAdapters.Searcher
        Searcher searcher = new Searcher();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the artist:");
        String artist = scanner.nextLine();
        List<String> artists = searcher.filterArtist(artist);
        // check if artist in database and check if not already following artist-- if so, append to following ArrayList
        if (artists.contains(artist)) {
            if (!follows.contains(artist)) {
                follows.add(artist);
            }
        }
    }


    /**
     * Removes an artist the Entities.RegisteredUser want to remove from their followings.
     */
    public void removeFollow() throws FileNotFoundException {
        // Asks for the input of the user for the artist to use in the InterfaceAdapters.Searcher
        Searcher searcher = new Searcher();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the artist:");
        String artist = scanner.nextLine();
        List<String> artists = searcher.filterArtist(artist);
        // check if artist in database -- if so, remove from following ArrayList
        if (artists.contains(artist)) {
            follows.remove(artist);
        }
    }

    /**
     * Gets all artists a registeredUser follows
     *
     * @return a List of artists the registeredUser follows
     */
    public List<Artist> getFollows() {
        return new ArrayList<Artist>();
    }
}