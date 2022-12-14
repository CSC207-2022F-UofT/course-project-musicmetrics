package use_cases;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArtistComparer {
    /**
     *
     * @param artistTwo     An artist's name
     * @return      A HashMap with artist name as keys
     *                  returns null if artistTwo does not have data correlating to artistOne's week/timestamp
     *
     * When comparing artists, the method will compare the two artists based on THE FIRST ARTIST'S timestamp.
     *      EX: if this artist instance is for week 3, then it will be compared against artistTwo's week 3.
     */
    public HashMap<String, Object> compare(String artistOne, String artistTwo, int week) {
        HashMap<String, Object> data = new HashMap<>();
        if (use_cases.MusicData.data.containsKey(week)) {
            data.put(artistOne, use_cases.MusicData.getArtistData(artistOne, week));
            data.put(artistTwo, use_cases.MusicData.getArtistData(artistTwo, week));
            return data;
        }
        return null;
    }

    /**
     * This method gets the top artists of the week and returns their names as a list
     *
     * @param amt - the number of artists names needed
     * @return names - a list of the top artists' names
     */
    public static List<String> topArtistNames(int amt) {
        return artistToNames(use_cases.MusicData.getTop(use_cases.MusicData.getLatestWeek(), amt));
    }

    /**
     * Returns an ArrayList of trending Artist names based on given parameters.
     *
     * @param startWeek the starting week number
     * @param endWeek the ending week number
     * @param amt the number of artists names needed
     * @return an ArrayList of trending Artists names
     */
    public static List<String> trendingArtistNames(int startWeek, int endWeek, int amt) {
        List<Artist> artists = MusicData.getTrending(startWeek, endWeek);
        List<String> names = new ArrayList<>();
        for (Artist artist : artists) {
            if (names.size() < amt) {
                names.add(artist.getName());
            }
            else {
                break;
            }
        }
        return names;
    }

    /**
     * Returns an ArrayList of Artist name given a List of Artist
     *
     * @param artists a List of Artist to convert to an ArrayList of names
     * @return an ArrayList of Artists names
     */
    public static List<String> artistToNames(List<Artist> artists) {
        List<String> names = new ArrayList<>();
        for (Artist artist : artists) {
            names.add(artist.getName());
        }
        return names;
    }
}
