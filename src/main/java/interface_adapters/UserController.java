package interface_adapters;
import use_cases.*;

import java.util.List;
import java.util.Scanner;

public class UserController {

    public static void followArtist(UserData userData, String email, String artist) throws Exception {
        userData.getUser(email).followArtist(MusicData.getArtistByName(artist));
    }

    public static void unfollowArtist(UserData userData, String email, String artist) throws Exception {
        userData.getUser(email).unfollowArtist(MusicData.getArtistByName(artist));
    }

    public static List<String> getFollowedArtistNames(UserData userData, String email) throws Exception {
        return ArtistComparer.artistToNames(userData.getUser(email).getFollows());
    }

}
