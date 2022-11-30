package use_cases;
import entities.Artist;
import entities.GuestUser;
import entities.RegisteredUser;
import entities.User;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class MusicData{
    /**
     * NOTE: Assume that an Artist will have information for EVERY WEEK in the Hashmap
     */
    public static HashMap<Integer, ArrayList<Artist>> data = new HashMap<>();


    /**
     * Compare past streams to a current week to see if an artist was trending during a given week
     * Trending is defined as an increase of 10% or 10 million streams across the weeks
     *
     * @param startWeek first week to compare streams from
     * @param endWeek second week to compare streams from
     * @return list of all artists trending this week
     */
    public static List<Artist> getTrending(int startWeek, int endWeek) {
        List<Artist> trending = new ArrayList<>();
        ArrayList<Artist> startData = data.get(startWeek);
        ArrayList<Artist> endData = data.get(endWeek);

        for (Artist a : startData){
            for (Artist b : endData){
                if (a.getName().equals(b.getName())){if (isTrending(a, b)){trending.add(b); } }
            }
        }

        return trending;
    }

    /**
     * Helper method for getTrending that returns a boolean value based on if trending criterea is met
     *
     * @param a Artist instance from earlier week
     * @param b Artist instance from later week
     * @return Boolean whether there has been a trending-worthy increase
     */
    public static Boolean isTrending(Artist a, Artist b){
        return (b.getStreams() > a.getStreams()*1.10 || b.getStreams() > a.getStreams() + 10000000);
    }





    /**
     *
     * @return  latest week in the Hashmap (aka the highest integer)
     */
    public static int getLatestWeek() {
        return Collections.max(data.keySet());
    }


    /**
     * Gets all streams of a given artist for given weeks
     *
     * @param artist the artist whose streams will be returned
     * @param weeks the weeks of stream data that will be accessed
     * @return a hashmap of weeks to streams
     */
    public static HashMap<Integer, Integer> getStreams(Artist artist, List<Integer> weeks) {
        HashMap<Integer,Integer> to_return = new HashMap<>();
        String name = artist.getName();
        for (int i: weeks) {
            to_return.put(i, getStreamsHelper(name, i));
        }

        return to_return;
    }


//    Helper method for getStreams that returns artist streams of a week
//    Assumes artists for MusicData is stored in a key(week) to value(array of artists relationship)
    private static int getStreamsHelper(String name, int week) {
        List<Artist> week_data = getArtists(week);
        for (Artist artist: week_data) {
            if (artist.getName().equals(name)) {
                return artist.getStreams();
            }
        }
        return 0;
    }

//    returns list of artist objects for current week
    public static ArrayList<Artist> getArtists(int week){
        return data.get(week);
    }


    /**
     *
     * @param artist An Artist Object
     */
    public static void addArtist(Artist artist) {
        if (data.containsKey(artist.time)) {
            data.get(artist.time).add(artist);
            }
        if (!(data.containsKey(artist.time))){
            ArrayList<Artist> artistList = new ArrayList<Artist>();
            artistList.add(artist);
            data.put(artist.time, artistList);
        }
    }


    /**
     * Retrieve all artists within given genre
     *
     * @param genre the genre being searched for
     * @return list of all Artists in that genre
     */
    public static List<Artist> getArtistsByGenre(String genre){
        int week = getLatestWeek();
        List<Artist> artist_in_genre = new ArrayList<>();
        ArrayList<Artist> w_data = getArtists(week);

        for (Artist a : w_data){
            if (a.getGenre().equals(genre)){artist_in_genre.add(a); } //add artist to list if genre matches
        }

        return artist_in_genre;
    }


    /**
     * gets all genres in MusicData files
     *
     * @return list of unique genres that artists are in
     */
    public static List<String> getGenres(){
        int week = getLatestWeek();
        List<String> all_genres = new ArrayList<>();
        for (int w = 1; w<=week; w++) {
            ArrayList<Artist> w_data = getArtists(w);
            for (Artist a : w_data) {
                if (!(all_genres.contains(a.getGenre()))) {
                    all_genres.add(a.getGenre());
                }
            }
        }
        return all_genres.stream().distinct().collect(Collectors.toList());
    }


    /**
     * Gets the top artists of the week by streams
     *
     * @param week the current week for which we want the top artist
     * @param amount how many top Artists are we looking for
     * @return List of Artists who had the top streams of the week
     */
    public static List<Artist> getTop(int week, int amount){
        ArrayList<Artist> w_data = getArtists(week);
        List<Artist> top = new ArrayList<>();

        List<Integer> streams = new ArrayList<>();
        for (Artist a: w_data) {
            streams.add(a.getStreams());
        }
        Collections.sort(streams);
        Collections.reverse(streams);

        for (int amt = 0; amt < amount; amt++){
            for (Artist a : w_data){
                if (a.getStreams() == streams.get(amt)){
                    top.add(a);
                    break;
                }
            }
        }

        return top;
    }


    public static HashMap<Integer, Integer> getFollows(Artist artist, List<Integer> weeks) {
        HashMap<Integer, Integer> follows = new HashMap<>();
        String artistName = artist.getName();
        for (int week : weeks) {
            follows.put(week, getFollowsHelper(artistName, week));
        }

        return follows;
    }

    private static int getFollowsHelper(String name, int week) {
        List<Artist> weekData = getArtists(week);
        for (Artist artist: weekData) {
            if (artist.getName().equals(name)) {
                return artist.getFollows();
            }
        }
        return 0;
    }

    /**
     * return info of artist needed
     *
     * @param artist name of artist we need data for
     * @param week current week
     * @return A hashmap that represents artist data
     *      returns null if artist name is not in the arrayList of the given week
     *
     * PLEASE ASSUME THAT AN ARTIST WILL ALWAYS BE IN THE ARRAYLIST OF THE GIVEN WEEK (KEY)
     */
    public static HashMap<String, Object> getArtistData(String artist, int week){
        Artist artistObj = null;
        for (Artist a : data.get(week)) {
            if (a.getName().equals(artist)) {artistObj = a; }
        }

        if (!(artistObj==null)) {
            return artistObj.getInfo();
        }
        return null;
    }

    /**
     * Returns the information an Artist whose name is the given parameter name.
     * Returns an empty HashMap if Artist with name is not found in the MusicData.
     *
     * @param name the name of the artist
     * @return information of an Artist with the given name stored in HashMap
     */
    public static HashMap<String, Object> getArtistInfoByName(String name) {
        List<Artist> artists = MusicData.getArtists(MusicData.getLatestWeek());
        for (Artist artist : artists) {
            if (artist.getName().equals(name)) {
                return artist.getInfo();
            }
        }
        return new HashMap<>();
    }

    /**
     * Returns an Artist whose name is the given parameter name.
     * Returns null if Artist with name is not found in the MusicData.
     *
     * @param name the name of the artist
     * @return an Artist with the given name
     */
    public static Artist getArtistByName(String name) {
        List<Artist> artists = MusicData.getArtists(MusicData.getLatestWeek());
        for (Artist artist : artists) {
            if (artist.getName().equals(name)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * Returns a List of relevant information based on the given action.
     * Returns null if the given action is not available.
     * Available actions: top, trending
     *
     * @param action the action that User chose to take
     * @return a List of relevant information
     */
    public static List<String> actionResult(String action) {
        String[] split = action.split(" ");
        if (action.startsWith("top")) {
            return ArtistComparer.topArtistNames(Integer.parseInt(split[1]));
        }
        else if (action.contains("trending")) {
            return ArtistComparer.trendingArtistNames(1, MusicData.getLatestWeek(), Integer.parseInt(split[0]));
        }
        return null;
    }

    /**
     * Returns an ArrayList of the name of Artists within the given genre.
     *
     * @param genre the name of the genre
     * @return an ArrayList of the name of Artists with the given genre
     */
    public static List<String> getArtistsNameByGenre(String genre) {
        List<Artist> artists = getArtistsByGenre(genre);
        List<String> names = new ArrayList<>();
        for (Artist artist : artists) {
            names.add(artist.getName());
        }
        return names;
    }

    public static void main(String[] args) throws FileNotFoundException {
        MusicDataBuilder.setData();
        System.out.println(getGenres());
    }

}
