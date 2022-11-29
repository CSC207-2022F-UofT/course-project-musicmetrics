package use_cases;
import entities.Artist;
import entities.GuestUser;
import entities.RegisteredUser;
import entities.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class MusicData{
    /**
     * NOTE: Assume that an Artist will have information for EVERY WEEK in the Hashmap
     */
    public static HashMap<Integer, ArrayList<Artist>> data = new HashMap<>();


    /**
     * reads from relevant data files and stores info in data Hashmap
     *
     * @throws FileNotFoundException for Scanner sc
     */
    public static void setData() throws FileNotFoundException {

        for (int week=1; week<=3; week++) {
            Scanner sc = new Scanner(new File("src/main/java/use_cases/music_database/Data_" + week));
            sc.useDelimiter(", ");
            sc.nextLine();
            ArrayList<Artist> allArtists = new ArrayList<>();
            while (sc.hasNextLine()) {
//                System.out.println(week);
//                System.out.println(sc.next());
//                System.out.println(sc.next());
//                System.out.println(sc.next());
//                System.out.println(sc.next());
//                System.out.println(sc.next());

                Artist a = setArtistData(sc, week);
                allArtists.add(a); //add Artist to allArtists arraylist
            }
            data.put(week, allArtists); //store information in data HashMap
            sc.close();
        }
    }


    /**
     * Reads info from relevant file and stores in a new Artist instance
     *
     * @param sc Scanner passed from setData to read this line
     * @param week Current week and file data is extracted from
     *
     * @return Artist instance with current week's info stored
     */
    public static Artist setArtistData(Scanner sc, int week){
        String name, genre;
        int streams, follows;

        //read all info
        name = sc.next();
        streams = Integer.parseInt(sc.next());
        follows = Integer.parseInt(sc.next());
        genre = sc.next();
        Boolean[] likes = likesConverter(sc.next());

        return new Artist(follows, genre, name, likes, week, streams);
    }


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

//        StringBuilder to_return = new StringBuilder();
//        for (Artist a : trending){
//            to_return.append(a.getName());
//            to_return.append("\n");
//        }
//        return to_return;
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

    /**
     * converts a string of 1s and 0s into a boolean array
     *
     * @param binary a string of 1s and 0s to represent likes
     * @return boolean array corresponding to binary
     */
    private static Boolean[] likesConverter(String binary) {
        String[] binaryList = binary.split(" ");
        Boolean[] likes = new Boolean[binary.length()];
        for(int i = 0; i < binaryList.length; i++){
            String s = binaryList[i];
            likes[i] = s.equals("1");
        }
        return likes;
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
     * Returns an Artist whose name is the given parameter name.
     * Returns null if Artist with name is not found in the MusicData.
     *
     * @param name the name of the artist
     * @return an Artist with the given name
     */
    public static Artist artistResult(String name) {
        List<Artist> artists = MusicData.getArtists(MusicData.getLatestWeek());
        for (Artist artist : artists) {
            if (artist.getName().equals(name)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * Prints out relevant information based on the given action.
     * Available actions: top, recommend
     *
     * @param action the action that User chose to take
     */
    public static void actionResult(String action) {
        if (action.startsWith("top")) {
            String[] split = action.split(" ");
            int amt = Integer.parseInt(split[1]);
            List<String> artists = use_cases.ArtistComparer.topArtistNames(amt);
            for (int i = 0;i < artists.size();i++) {
                System.out.println((i + 1) + ". " + artists.get(i));
            }
        }
        else if (action.startsWith("recommend")) {
            String genre = action.substring(10, action.indexOf("artist") - 1);
            MusicData mD = new MusicData();
            GuestUser gU = new GuestUser();
            Artist artist = RecommendArtist.recommend(genre, false, gU);
            for (Map.Entry<String, Object> entry : artist.getInfo().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    /**
     * Returns an ArrayList of Artists within the given genre.
     *
     * @param genre the name of the genre
     * @return an ArrayList of Artist with the given genre
     */
    public List<Artist> genreResult(String genre) {
        return MusicData.getArtistsByGenre(genre);
    }

    public static void main(String[] args) throws FileNotFoundException {
        MusicData.setData();
        System.out.println(getGenres());
    }

}
