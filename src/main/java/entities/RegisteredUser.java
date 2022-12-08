package entities;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class RegisteredUser extends entities.User {
    private final String email;
    private String password;
    public ArrayList<String> follows;

    public RegisteredUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.follows = FollowsBuilder.followsMap.get(email);
    }

    public String toString() { return this.email;}

    /** Checks whether the user has the permission (i.e., follow permission)
     *
     * @return a boolean whether the User has the permission
     */
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

    /** Sets the user's follow
     *
     * @param follows following ArrayList to set
     */
    public void setFollows(ArrayList<String> follows) {this.follows = follows; }

    /**
     * Adds an artist the Entities.RegisteredUser want to remove from their followings.
     * @param artist the artist user wants to add
     */
     
    public void followArtist(String artist) throws IOException {
        this.follows.add(artist);
        addToFollows(artist);
    }

    /**
     * Removes an artist the Entities.RegisteredUser want to remove from their followings.
     * @param artist the artist user wants to remove
     */

    public void unfollowArtist(String artist) throws IOException {
        this.follows.remove(artist);
        removeFromFollows(artist);
    }

    /**
     *
     * @param toAdd string of Artist to add to the user's followed artist
     *
     *
     * Updates the text file for data persistence
     */
    public void addToFollows(String toAdd) throws IOException {
        File inputFile = new File("src/main/java/entities/Follows");
        File tempFile = new File("src/main/java/entities/FollowsIntermediate");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            String[] tmp = trimmedLine.split(", ");
            if (Objects.equals(tmp[0], this.email)) {
                writer.write(currentLine + ", " + toAdd);
                writer.write(System.getProperty("line.separator"));
            } else {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();
        tempFile.renameTo(inputFile);
    }

    /**
     *
     * @param toRemove string of Artist to remove from user's following
     *
     * Updates the text file for data persistence
     */
    public void removeFromFollows(String toRemove) throws IOException {
        if (!follows.contains(toRemove)) {
            return;
        }
        File inputFile = new File("src/main/java/entities/Follows");
        File tempFile = new File("src/main/java/entities/FollowsIntermediate");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            String[] tmp = trimmedLine.split(", ");
            if (Objects.equals(tmp[0], this.email)) {
                final List<String> list = new ArrayList<String>();
                Collections.addAll(list, tmp);
                String header = list.remove(0);
                list.remove(toRemove);
                tmp = list.toArray(new String[0]);

                writer.write(header);

                for (String i : tmp) {
                    writer.write(", " + i);
                }
                writer.write(System.getProperty("line.separator"));
            } else {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();
        tempFile.renameTo(inputFile);
    }

    /**
     * Gets all artists a registeredUser follows
     *
     * @return a List of artists the registeredUser follows
     */
    public ArrayList<String> getFollows() {
        return this.follows;
    }
}
