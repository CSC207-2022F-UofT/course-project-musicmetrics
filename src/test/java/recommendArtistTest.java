import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class recommendArtistTest {

        @Test()
        public void TestRandom() {
                Artist Drake = new Artist(1000, "Hip-Hop/Rap", "Drake",
                        new Boolean[] {true, false, false, false, true}, 1, 1000);
                Artist Jcole = new Artist(1000, "Hip-Hop/Rap", "Jcole",
                        new Boolean[] {true, true, false, false, false}, 1, 1000);
                Artist Kendrick = new Artist(1000, "Hip-Hop/Rap", "Kendrick",
                        new Boolean[] {true, true, false, false, true}, 1, 1000);
                Artist Taylor = new Artist(1000, "Pop", "Taylor Swift",
                        new Boolean[] {true, true, false, false, true}, 1, 1000);
                ArrayList<Artist> week1Artist = new ArrayList<>();
                week1Artist.add(Drake);
                week1Artist.add(Jcole);
                week1Artist.add(Kendrick);
                week1Artist.add(Taylor);
                MusicData.data.put(1, week1Artist);

                GuestUser gU = new GuestUser();
                MusicData mD = new MusicData();
                Artist reccommendation = mD.recommendArtist("Hip-Hop/Rap", false, gU);
                Assertions.assertEquals("Hip-Hop/Rap", reccommendation.getGenre());
                reccommendation = mD.recommendArtist("Pop", false, gU);
                Assertions.assertEquals("Taylor Swift", reccommendation.getName());
        }


        @Test()
        public void TestSimilarity() {
                /*
                Artist Drake = new Artist(1000, "Hip-Hop/Rap", "Drake",
                        new Boolean[] {true, false, false, false, true}, 1, 1000);
                Artist Jcole = new Artist(1000, "Hip-Hop/Rap", "Jcole",
                        new Boolean[] {true, true, false, false, false}, 1, 1000);
                Artist Kendrick = new Artist(1000, "Hip-Hop/Rap", "Kendrick",
                        new Boolean[] {true, true, false, false, true}, 1, 1000);
                ArrayList<Artist> week1Artist = new ArrayList<>();
                week1Artist.add(Drake);
                week1Artist.add(Jcole);
                week1Artist.add(Kendrick);
                MusicData.data.put(1, week1Artist);

                RegisteredUser rUser = new RegisteredUser("test@gmail.com", "password");
                rUser.addFollow(Kendrick);
                MusicData mD = new MusicData();
                Artist reccommendation = mD.recommendArtist("Hip-Hop/Rap", false, rUser);
                Assertions.assertEquals("Hip-Hop/Rap", reccommendation.getGenre());
                reccommendation = mD.recommendArtist("Pop", false, rUser);
                Assertions.assertEquals("Taylor Swift", reccommendation.getName());
                 */
        }

}
