import entities.Artist;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_cases.MusicData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

/// This test DOES NOT use the data from music_database
/// INSTEAD it uses a narrower set of data, containing 4 artists as seen below
public class MusicDataTest {

    @Test()
    public void TestHashMap() throws FileNotFoundException {
        MusicData test = new MusicData();
        MusicData.setData();
        List<Integer> testweek = new ArrayList<>();
        testweek.add(1);

        //Test the length of Hashmap and if it correctly added the artists in database
        Assertions.assertEquals(MusicData.data.get(1).size(), 14);

        //Test that data hashmap has correct latest week
        Assertions.assertEquals(MusicData.getLatestWeek(),3);

        //Test getArtistData
        Assertions.assertEquals(Objects.requireNonNull(MusicData.getArtistData("Drake", 1)).get("TimeStamp"),  1);
        Assertions.assertEquals(Objects.requireNonNull(MusicData.getArtistData("Drake", 1)).get("Genre"),  "Hip-Hop/Rap");
        Assertions.assertEquals(Objects.requireNonNull(MusicData.getArtistData("Drake", 1)).size(),  5);

        //Test getArtistsByGenre
        Assertions.assertEquals(6, MusicData.getArtistsByGenre("Pop").size());
        Assertions.assertNotEquals(4, MusicData.getArtistsByGenre("Hip-Hop/Rap").size());


        //Test addArtist and getArtist
        Assertions.assertEquals(14, MusicData.getArtists(1).size());
        Artist Tester = new Artist(2000, "Country", "Tester",
                new Boolean[] {true, false, false, false, true}, 1, 1000);
        MusicData.addArtist(Tester);
        //Length should be 15 since 1 new artist was added
        Assertions.assertEquals(MusicData.data.get(1).size(), 15);
        Assertions.assertEquals(15, MusicData.getArtists(1).size());

        //Test getStreams
        Assertions.assertEquals(MusicData.getStreams(Tester, testweek).get(1), 1000);

        //Test getFollows
        Assertions.assertEquals(MusicData.getFollows(Tester, testweek).get(1),2000);

        //Test getTop
        Assertions.assertEquals(1, MusicData.getTop(1, 1).size());
        Assertions.assertEquals("Drake", MusicData.getTop(1, 1).get(0).toString());

        //Test getGenres
        Assertions.assertTrue(MusicData.getGenres().contains("Pop"));
        Assertions.assertTrue(MusicData.getGenres().contains("Hip-Hop/Rap"));
        Assertions.assertFalse(MusicData.getGenres().contains("TESTGENRE"));
        Artist TesterGenre = new Artist(2000, "TESTGENRE", "TesterGenre",
                new Boolean[] {true, false, false, false, true}, 1, 1000);
        MusicData.addArtist(TesterGenre);
        Assertions.assertTrue(MusicData.getGenres().contains("TESTGENRE"));

        //Test getTrending
        Artist TesterGenre2 = new Artist(2000, "TESTGENRE", "TesterGenre",
                new Boolean[] {true, false, false, false, true}, 2, 1000);
        MusicData.addArtist(TesterGenre2);
        Assertions.assertFalse(MusicData.getTrending(1, 2).contains(TesterGenre2));

        //Test recommendArtist

    }

}
