import entities.Artist;
import entities.GuestUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_cases.MusicData;
import use_cases.MusicDataBuilder;
import use_cases.RecommendArtist;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RecommendArtistTest {

        @Test()
        public void TestRandom() throws FileNotFoundException {
                //reset data
                MusicDataBuilder.setData();

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
                //Tests if a random artist is recommended
                Artist reccommendation = RecommendArtist.recommend("Hip-Hop/Rap", false, gU);
                Assertions.assertEquals("Hip-Hop/Rap", reccommendation.getGenre());
        }


        @Test()
        public void TestSimilarity() throws FileNotFoundException {
                //The user must be registered (otherwise similar would be false)
                //The user can only select from the valid provided genres (all with atleast one artist)
                //reset data
                MusicDataBuilder.setData();

                entities.RegisteredUser rUser = new entities.RegisteredUser("test@gmail.com", "password");

                //follow is within same genre
                entities.Artist Drake = new entities.Artist(1000, "Hip-Hop/Rap", "Drake",
                        new Boolean[] {false, false, false, false, false, true}, 1, 1000);
                entities.Artist Jcole = new entities.Artist(1000, "Hip-Hop/Rap", "Jcole",
                        new Boolean[] {true, true, false, false, false, false}, 1, 1000);
                entities.Artist Kendrick = new entities.Artist(1000, "Hip-Hop/Rap", "Kendrick",
                        new Boolean[] {true, true, false, false, true, false}, 1, 1000);
                ArrayList<entities.Artist> week1Artist = new ArrayList<>();
                week1Artist.add(Drake);
                week1Artist.add(Jcole);
                week1Artist.add(Kendrick);
                use_cases.MusicData.data.put(3, week1Artist);

                ArrayList<entities.Artist> userFollows = new ArrayList<>();
                rUser.setFollows(userFollows);

                //edge case User Follows No one
                Artist reccommendation = RecommendArtist.recommend("Hip-Hop/Rap", false, rUser);
                Assertions.assertEquals("Hip-Hop/Rap", reccommendation.getGenre());

                //user follows someone
                userFollows.add(Kendrick);
                rUser.setFollows(userFollows);
                use_cases.MusicData mD = new use_cases.MusicData();
                entities.Artist recommendationRap = RecommendArtist.recommend("Hip-Hop/Rap", true, rUser);
                Assertions.assertEquals("Jcole",recommendationRap.getName());

                //follow is within different genre
                Artist Ariana = new Artist(1000, "Pop", "Ariana Grande",
                        new Boolean[] {false, false, true, true, false, true}, 1, 1000);
                Artist Taylor = new Artist(1000, "Pop", "Taylor Swift",
                        new Boolean[] {true, true, false, false, false, false}, 1, 1000);
                week1Artist.add(Ariana);
                week1Artist.add(Taylor);
                use_cases.MusicData.data.put(3, week1Artist);
                entities.Artist recommendationPop = RecommendArtist.recommend("Pop", true, rUser);
                Assertions.assertEquals("Taylor Swift", recommendationPop.getName());

                //edge case User Follows everyone
                userFollows.add(Drake);
                userFollows.add(Jcole);
                Artist reccommendationEveryone = RecommendArtist.recommend("Hip-Hop/Rap", false, rUser);
                Assertions.assertEquals("Hip-Hop/Rap", reccommendationEveryone.getGenre());
        }

}
