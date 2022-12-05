import entities.Artist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_cases.MusicData;
import use_cases.MusicDataBuilder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/// This is the test class for MusicData
/// it tests methods in MusicData, against the data in music_database, AS WELL AS additional data

public class MusicDataTest {

    @Test()
    public void TestHashMap() throws FileNotFoundException {
        MusicDataBuilder.setData();
        List<Integer> testweek = new ArrayList<>();
        testweek.add(1);

        //Test the length of Hashmap and if it correctly added the artists in database
        Assertions.assertEquals(MusicData.data.get(1).size(), 14);

        //Test that data hashmap has correct latest week
        Assertions.assertEquals(MusicData.getLatestWeek(),3);
        Artist Tester1 = new Artist(1000, "genre", "tester",
                new Boolean[] {true, false, false, false, true}, 4, 1000);
        MusicData.addArtist(Tester1);
        Assertions.assertEquals(MusicData.getLatestWeek(),4);

        // test removeArtist and getLatestWeek
        MusicData.removeArtist("tester");
        Assertions.assertEquals(MusicData.getLatestWeek(),3);
        Artist Tester2 = new Artist(1000, "genre", "tester2",
                new Boolean[] {true, false, false, false, true}, 4, 1000);
        Artist Tester3 = new Artist(1000, "genre", "tester3",
                new Boolean[] {true, false, false, false, true}, 5, 1000);
        MusicData.addArtist(Tester2);
        Assertions.assertEquals(MusicData.getLatestWeek(),4);
        MusicData.addArtist(Tester3);
        Assertions.assertEquals(MusicData.getLatestWeek(),5);
        MusicData.removeArtist("tester2"); //latest week should still be 5 after removal, but week 4 is now empty
        Assertions.assertEquals(MusicData.getLatestWeek(),5);
        MusicData.removeArtist("tester3"); //removing this artist will make weeks 4 and 5 have empty arraylists
        //latest week should now be 3, since week 5 was empty it is removed, which then week 4 would be removed
        Assertions.assertEquals(MusicData.getLatestWeek(),3);

        //Test getArtistData
        Assertions.assertEquals(Objects.requireNonNull(MusicData.getArtistData("Drake", 1)).get("TimeStamp"),  1);
        Assertions.assertEquals(Objects.requireNonNull(MusicData.getArtistData("Drake", 1)).get("Genre"),  "Hip-Hop/Rap");
        Assertions.assertEquals(Objects.requireNonNull(MusicData.getArtistData("Drake", 1)).size(),  5);

        //Test getArtistByName
        Assertions.assertNull(MusicData.getArtistByName("BAD NAME")); //An artist name that doesn't exist in database
        Assertions.assertNotNull(MusicData.getArtistByName("Drake"));

        //Test getArtistsNameByGenre
        Assertions.assertNull(MusicData.getArtistsNameByGenre("NON EXISTENT")); //a non-existent genre
        Assertions.assertNotNull(MusicData.getArtistsNameByGenre("Pop"));
        Assertions.assertEquals(Objects.requireNonNull(MusicData.getArtistsNameByGenre("Hip-Hop/Rap")).size(), 3);

        //Test getArtistsByGenre
        Assertions.assertNull(MusicData.getArtistsByGenre("NON EXISTENT")); //a non-existent genre
        Assertions.assertEquals(6, Objects.requireNonNull(MusicData.getArtistsByGenre("Pop")).size());
        Assertions.assertNotEquals(4, Objects.requireNonNull(MusicData.getArtistsByGenre("Hip-Hop/Rap")).size());

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
    }

}
