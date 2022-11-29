package use_cases;
import entities.Artist;

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
        List<Artist> artists = use_cases.MusicData.getTop(use_cases.MusicData.getLatestWeek(), amt);
        List<String> names = new ArrayList<String>();
        for (Artist a : artists){names.add(a.getName()); }
        return names;
    }
}
