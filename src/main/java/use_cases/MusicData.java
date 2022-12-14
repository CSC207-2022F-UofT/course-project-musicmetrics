package use_cases;

import entities.*;

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
    private static Boolean isTrending(Artist a, Artist b){
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

    /**
     * Helper method for getStreams that returns artist streams of a week
     * Assumes artists for MusicData is stored in a key(week) to value(array of artists relationship)
     *
     * @param name a string representing artist name
     * @param week integer representing a week
     * @return an integer representing streaming number
     */
    private static int getStreamsHelper(String name, int week) {
        List<Artist> week_data = getArtists(week);
        for (Artist artist: week_data) {
            if (artist.getName().equals(name)) {
                return artist.getStreams();
            }
        }
        return 0;
    }

    /** Returns an ArrayList of Artist of the given week
     *
     * @param week the data week
     * @return an ArrayList of Artist
     */
    public static ArrayList<Artist> getArtists(int week){
        return data.get(week);
    }

    /**
     * FOR TESTING PURPOSES
     * Method used to add an artist to the data hashmap
     *
     * @param artist An Artist Object
     */
    public static void addArtist(Artist artist) {
        if (data.containsKey(artist.time)) {
            data.get(artist.time).add(artist);
            }
        if (!(data.containsKey(artist.time))){
            ArrayList<Artist> artistList = new ArrayList<>();
            artistList.add(artist);
            data.put(artist.time, artistList);
        }
    }

    /**
     * FOR TESTING PURPOSES
     * Removes artist from the data hashmap
     * weeks do not need to be specified because it is assumed that if an artist is in the hashmap,
     * then it has data for every week
     *
     * @param artist a string representing an artist name
     */
    public static void removeArtist(String artist) {
        for (int k : data.keySet()) {
            data.get(k).removeIf(a -> Objects.equals(a.getName(), artist));
            if (data.get(getLatestWeek()).size() == 0) {data.remove(getLatestWeek());}
            // ^^ Removes the latest week if the artist that was removed was the only artist in that week
            // We remove the latest week if it is empty for testing purposes and continuity
            // If a week is empty, and it is not the latest week, then we keep it in the hashmap.
        }
        if (data.get(getLatestWeek()).size() == 0) {data.remove(getLatestWeek());}
    }

    /**
     * Retrieve all artists within given genre
     * return null if genre does not exist or there are no artists
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
        if (artist_in_genre.size() >= 1) {return artist_in_genre;}
        else {return null;}
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

    /**
     *
     * @param artist an instance of artist
     * @param weeks a list containing integers that represent a week
     * @return a hashmap with an integer week as a key,
     *          and an integer representing the amount of follows the artist has for that week
     */
    public static HashMap<Integer, Integer> getFollows(Artist artist, List<Integer> weeks) {
        HashMap<Integer, Integer> follows = new HashMap<>();
        String artistName = artist.getName();
        for (int week : weeks) {
            follows.put(week, getFollowsHelper(artistName, week));
        }

        return follows;
    }

    /**
     * Helper method used in getFollows
     *
     * @param name a string representing the name of an artist
     * @param week an integer representing a week
     * @return an integer representing the number of follows for the artist at a given week
     */
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
            if (a.getName().equals(artist)) {
                artistObj = a;
            }
        }
        if (!(artistObj == null)) {
            return artistObj.getInfo();
        }
        return null;
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
     * Returns an ArrayList of the name of Artists within the given genre.
     * Returns null if genre does not exist in our database
     *
     * @param genre the name of the genre
     * @return an ArrayList of the name of Artists with the given genre
     */
    public static List<String> getArtistsNameByGenre(String genre) {
        List<Artist> artists = MusicData.getArtistsByGenre(genre);
        List<String> names = new ArrayList<>();
        if (artists == null) {
            return null;
        }
        for (Artist artist : artists) {
            names.add(artist.getName());
        }
        if (names.size() >= 1) {
            return names;
        }
        else {
            return null;
        }
    }
}
