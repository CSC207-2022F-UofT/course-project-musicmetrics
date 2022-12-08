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
        RegisteredUser user = new RegisteredUser("something@yahoo.com", "1111");
        Artist Drake = new Artist(1000, "Hip-Hop/Rap", "Drake",
                new Boolean[]{true, false, false, false, true}, 1, 1000);
        Artist Jcole = new Artist(1000, "Hip-Hop/Rap", "Jcole",
                new Boolean[]{true, true, false, false, false}, 1, 1000);
        Artist Kendrick = new Artist(1000, "Hip-Hop/Rap", "Kendrick",
                new Boolean[]{true, true, false, false, true}, 1, 1000);
        Artist Taylor = new Artist(1000, "Pop", "Taylor Swift",
                new Boolean[]{true, true, false, false, true}, 1, 1000);

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(Drake);
        artists.add(Jcole);
        artists.add(Kendrick);
        artists.add(Taylor);
        user.setFollows(artists);
        MusicData.data.put(1, artists);

        Artist Drake02 = new Artist(1000, "Hip-Hop/Rap", "Drake",
                new Boolean[]{true, false, false, false, true}, 2, 2000);
        Artist Jcole02 = new Artist(1000, "Hip-Hop/Rap", "Jcole",
                new Boolean[]{true, true, false, false, false}, 2, 50);
        Artist Kendrick02 = new Artist(1000, "Hip-Hop/Rap", "Kendrick",
                new Boolean[]{true, true, false, false, true}, 2, 5);
        Artist Taylor02 = new Artist(1000, "Pop", "Taylor Swift",
                new Boolean[]{true, true, false, false, true}, 2, 200);
        ArrayList<Artist> artists02 = new ArrayList<>();
        artists02.add(Drake02);
        artists02.add(Jcole02);
        artists02.add(Kendrick02);
        artists02.add(Taylor02);
        user.setFollows(artists02);
        MusicData.data.put(2, artists02);

        Alert a = new Alert(user);
        a.trigger();
        HashMap<String, Float> NameGrowth = new HashMap<>();
        NameGrowth.put("Drake", (float) 2);
        Assertions.assertEquals(a.getTop(), NameGrowth);
    }
}
