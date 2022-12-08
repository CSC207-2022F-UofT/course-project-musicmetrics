import entities.Artist;
import entities.RegisteredUser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import use_cases.Alert;
import use_cases.MusicData;
import java.util.ArrayList;
import java.util.HashMap;

public class AlertTest {
    @Test
    public void TriggerTest() {

        //testing alert when user have multiple followings with different streams
        // and with different growth rates for alert that user chooses
        RegisteredUser user = new RegisteredUser("something@yahoo.com", "1111");
        entities.Artist Drake = new entities.Artist(1000, "Hip-Hop/Rap", "Drake",
                new Boolean[] {false, false, false, false, false, true}, 1, 1000);
        entities.Artist Eminem = new entities.Artist(1000, "Hip-Hop/Rap", "Eminem",
                new Boolean[] {true, true, false, false, false, false}, 1, 1000);
        entities.Artist Post = new entities.Artist(1000, "Hip-Hop/Rap", "Post Malone",
                new Boolean[] {true, true, false, false, true, false}, 1, 1000);
        Artist Taylor = new Artist(1000, "Pop", "Taylor Swift",
                new Boolean[]{true, true, false, false, true}, 1, 1000);

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(Drake);
        artists.add(Eminem);
        artists.add(Post);
        artists.add(Taylor);
        ArrayList<String> artistsNames = new ArrayList<>();
        artistsNames.add(Drake.getName());
        artistsNames.add(Eminem.getName());
        artistsNames.add(Post.getName());
        artistsNames.add(Taylor.getName());
        user.setFollows(artistsNames);
        MusicData.data.put(1, artists);

        Artist Drake02 = new Artist(1000, "Hip-Hop/Rap", "Drake",
                new Boolean[]{true, false, false, false, true}, 2, 2000);
        Artist Eminem02 = new Artist(1000, "Hip-Hop/Rap", "Eminem",
                new Boolean[]{true, true, false, false, false}, 2, 50);
        Artist Post02 = new Artist(1000, "Hip-Hop/Rap", "Post Malone",
                new Boolean[]{true, true, false, false, true}, 2, 5);
        Artist Taylor02 = new Artist(1000, "Pop", "Taylor Swift",
                new Boolean[]{true, true, false, false, true}, 2, 200);
        ArrayList<Artist> artists02 = new ArrayList<>();
        artists02.add(Drake02);
        artists02.add(Eminem02);
        artists02.add(Post02);
        artists02.add(Taylor02);
        ArrayList<String> artistsNames02 = new ArrayList<>();
        artistsNames02.add(Drake02.getName());
        artistsNames02.add(Eminem02.getName());
        artistsNames02.add(Post02.getName());
        artistsNames02.add(Taylor02.getName());
        user.setFollows(artistsNames02);
        MusicData.data.put(2, artists02);

        Alert a = new Alert(user);
        a.trigger(1.25);
        HashMap<String, Float> NameGrowth = new HashMap<>();
        NameGrowth.put("Drake", (float) 2);
        Assertions.assertEquals(a.getTop(), NameGrowth);

        a.trigger(0.0);
        HashMap<String, Float> NoGrowth = new HashMap<>();
        NoGrowth.put("Drake", (float) 2);
        NoGrowth.put("Eminem", (float) 0.05);
        NoGrowth.put("Post Malone", (float) 0.005);
        NoGrowth.put("Taylor Swift", (float) 0.2);
        Assertions.assertEquals(a.getTop(), NoGrowth);


        // Testing alert when the user has no followings.
        ArrayList<String> NoFollows = new ArrayList<String>();
        user.setFollows(NoFollows);
        Alert b = new Alert(user);
        b.trigger(1.0);
        HashMap<String, Float> No = new HashMap<>();
        Assertions.assertEquals(b.getTop(), No);

        //Testing alert when the user has one following.
        ArrayList<String> OnoFollows = new ArrayList<String>();
        OnoFollows.add(Drake.getName());
        user.setFollows(OnoFollows);
        Alert c = new Alert(user);
        c.trigger(1.25);
        HashMap<String, Float> One = new HashMap<>();
        One.put("Drake", (float) 2);
        Assertions.assertEquals(c.getTop(), One);



    }
}
