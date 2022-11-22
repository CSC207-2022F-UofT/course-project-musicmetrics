import entities.Artist;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_cases.MusicData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/// This test DOES NOT use the data from music_database
/// INSTEAD it uses a narrower set of data, containing 4 artists as seen below
public class MusicDataTest {

    @Test()
    public void TestHashMap() throws FileNotFoundException {
        MusicData test = new MusicData();
        // Created a new HashMap for the sole purpose of testing, to have a narrow data set of 4 artists
        Artist Drake = new Artist(1000, "Hip-Hop/Rap", "Drake",
                new Boolean[] {true, false, false, false, true}, 1, 1000);
        Artist Jcole = new Artist(1000, "Hip-Hop/Rap", "Jcole",
                new Boolean[] {true, true, false, false, false}, 1, 2000);
        Artist Kendrick = new Artist(1000, "Hip-Hop/Rap", "Kendrick",
                new Boolean[] {true, true, false, false, true}, 1, 3000);
        Artist Taylor = new Artist(1000, "Pop", "Taylor Swift",
                new Boolean[] {true, true, false, false, true}, 1, 1000);
        MusicData.data.put(1, new ArrayList<>());
        MusicData.data.put(2, new ArrayList<>());

        //Test the length of Hashmap and if it correctly added the artists
        test.addArtist(Drake);
        Assertions.assertEquals(MusicData.data.size(), 2);
        test.addArtist(Jcole);
        Assertions.assertEquals(MusicData.data.get(1).size(), 2);
        test.addArtist(Kendrick);
        Assertions.assertEquals(MusicData.data.get(1).size(), 3);
        test.addArtist(Taylor);
        Assertions.assertEquals(MusicData.data.get(1).size(), 4);

        //Test that data hashmap has correct latest week
        Assertions.assertEquals(MusicData.getLatestWeek(),2);
        List<Integer> testweek = new ArrayList<>();
        testweek.add(1);

        //Test getStreams
        Assertions.assertEquals(MusicData.getStreams(Drake, testweek).get(1), 1000);

        //Test follows
        Assertions.assertEquals(MusicData.getFollows(Drake, testweek).get(1),1000);

        //Test getTop
        // This one should only include Kendrick since he has the most streams and the amount = 1
        Assertions.assertTrue(MusicData.getTop(1, 1).contains(Kendrick));
        Assertions.assertFalse(MusicData.getTop(1, 1).contains(Drake));
        // This one should include Jcole and Kendrick since the amount = 2 and Jcole is 2nd highest streams
        Assertions.assertTrue(MusicData.getTop(1, 2).contains(Jcole));

        //Test getGenres
        Assertions.assertTrue(MusicData.getGenres().contains("Pop"));
        Assertions.assertTrue(MusicData.getGenres().contains("Hip-Hop/Rap"));

        //Test getTrending
        // Increased Kendrick's streams by 10000, for the purpose of testing getTrending
        Artist Drake2 = new Artist(1000, "Hip-Hop/Rap", "Drake",
                new Boolean[] {true, false, false, false, true}, 2, 1000);
        Artist Jcole2 = new Artist(1000, "Hip-Hop/Rap", "Jcole",
                new Boolean[] {true, true, false, false, false}, 2, 2000);
        Artist Kendrick2 = new Artist(1000, "Hip-Hop/Rap", "Kendrick",
                new Boolean[] {true, true, false, false, true}, 2, 13000);
        Artist Taylor2 = new Artist(1000, "Pop", "Taylor Swift",
                new Boolean[] {true, true, false, false, true}, 2, 1000);
        test.addArtist(Drake2);
        test.addArtist(Jcole2);
        test.addArtist(Kendrick2);
        test.addArtist(Taylor2);
        // Since Kendrick is the only artist who has an increase in streams, he should be the only artist in the list
        Assertions.assertTrue(MusicData.getTrending(1, 2).contains(Kendrick2));
        Assertions.assertFalse(MusicData.getTrending(1, 2).contains(Drake2));
        Assertions.assertFalse(MusicData.getTrending(1, 2).contains(Jcole2));
        Assertions.assertFalse(MusicData.getTrending(1, 2).contains(Taylor2));

    }

}
