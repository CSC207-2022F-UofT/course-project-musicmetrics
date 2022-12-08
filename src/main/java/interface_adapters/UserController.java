package interface_adapters;
import use_cases.*;

import java.util.List;

public class UserController {

    /**
     * Let logged-in user (currentUser) in UserData to follow the given artist
     *
     * @param userData UserData
     * @param email logged-in user's email
     * @param artist Artist name to follow
     */
    public static boolean followArtist(UserData userData, String email, String artist) throws Exception {
        if (!userData.getUser(email).getFollows().contains(MusicData.getArtistByName(artist))) {
            userData.getUser(email).followArtist(MusicData.getArtistByName(artist));
            return true;
        }
        return false;
    }

    /**
     * Let logged-in user (currentUser) in UserData to unfollow the given artist
     *
     * @param userData UserData
     * @param email logged-in user's email
     * @param artist Artist name to unfollow
     */
    public static void unfollowArtist(UserData userData, String email, String artist) throws Exception {
        userData.getUser(email).unfollowArtist(MusicData.getArtistByName(artist));
    }

    /**
     * Returns an ArrayList of Artist name that the logged-in user in userData has followed
     *
     * @param userData UserData
     * @param email logged-in user's email
     * @return an ArrayList of followed Artist name
     */
    public static List<String> getFollowedArtistNames(UserData userData, String email) throws Exception {
        return ArtistComparer.artistToNames(userData.getUser(email).getFollows());
    }

}
