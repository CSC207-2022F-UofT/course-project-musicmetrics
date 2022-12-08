package entities;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends entities.User {
    private final String email;
    private String password;
    public ArrayList<entities.Artist> follows;

    public RegisteredUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.follows = new ArrayList<>();
    }

    public String toString() { return this.email;}

    @Override
    public boolean checkPermissions() {
        return true;
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
    public void setFollows(ArrayList<entities.Artist> follows) {this.follows = follows; }

    /**
     * Adds an artist the Entities.RegisteredUser want to remove from their followings.
     * @param artist the artist user wants to add
     */
    public void followArtist(Artist artist){
        this.follows.add(artist);
    }

    /**
     * Removes an artist the Entities.RegisteredUser want to remove from their followings.
     * @param artist the artist user wants to remove
     */
    public void unfollowArtist(Artist artist){
        this.follows.remove(artist);
    }


    /**
     * Gets all artists a registeredUser follows
     *
     * @return a List of artists the registeredUser follows
     */
    public List<entities.Artist> getFollows() {
        return this.follows;
    }
}
