import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class MusicData{
    /**
     * NOTE: Assume that an Artist will have information for EVERY WEEK in the Hashmap
     */
    static HashMap<Integer, ArrayList<Artist>> data = new HashMap<>();

    /**
     * reads from relevant data files and stores info in data Hashmap
     *
     * @throws FileNotFoundException for Scanner sc
     */
    public void setData() throws FileNotFoundException {

        for (int week=1; week<=3; week++) {
            Scanner sc = new Scanner(new File("Data_" + week));
            sc.useDelimiter(",");
            sc.nextLine();
            ArrayList<Artist> allArtists = new ArrayList<>();
            while (sc.hasNextLine()) {
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
    public Artist setArtistData(Scanner sc, int week){
        String name, genre;
        int streams, follows;
        //read all info
        name = sc.next();
        streams = Integer.parseInt(sc.next());
        follows = Integer.parseInt(sc.next());
        genre = sc.next();
        Boolean[] likes = new Boolean[0];

        return new Artist(follows, genre, name, likes, week, streams);
    }

    public List<Artist> getTrending(int top, int startWeek, int endWeek) {
        return new ArrayList<>();
    }

    /**
     * Gets an artist recommendation within a specific genre, random if similar is false, otherwise uses the similarties
     * with artists the user follows.
     *
     * @param genre the genre the recommedation is within
     * @param similar if the recommendation should be based on the user's followers or random
     * @param rUser the user that is making the request, should be registeredUser if similar is true
     * @return an artist recommeneded for the User
     */
    public Artist recommendArtist(String genre, boolean similar, User rUser) {
        Artist recommendation = new Artist();
        List<Artist> sameGenre = getArtistsByGenre(genre);
        if(similar){
            //generate a recommendation off of similarity scores to followed artists within the genre
            //get the User's follows and initialize the current highest similarity score (the one of the recommendation)
            List<Artist> registeredUserFollows = ((RegisteredUser) rUser).getFollows(); //similar is true means user is registered
            double recommendationSimilarityScore = 0.0;
            for(Artist artistWithinGenre : sameGenre){
                //for each artist within the recommended genre, generate the total similarity score to artistWithinGenre
                double similarityScore = 0.0;
                Boolean[] artistSimilarties = artistWithinGenre.getLikes();
                for(Artist follow : registeredUserFollows){
                    Boolean[] followSimilarities = follow.getLikes();
                    for(int i = 0; i < artistSimilarties.length; i++){
                        if(followSimilarities[i] == artistSimilarties[i]){
                            similarityScore = similarityScore + 1;
                        }
                    }
                }
                //generate an average similarity score and then change the recommendation if the average is greater
                //than the average of the current recommendation
                similarityScore = similarityScore / registeredUserFollows.size();
                if(similarityScore > recommendationSimilarityScore){
                    recommendation = artistWithinGenre;
                    recommendationSimilarityScore = similarityScore;
                }
            }
        } else{
            //generate a recommendation randomly within the genre
            int randomInt = new Random().nextInt(sameGenre.size());
            recommendation = sameGenre.get(randomInt);
        }

        return recommendation;
    }

    /**
     *
     * @return  latest week in the Hashmap (aka highest integer)
     */
    static int getLatestWeek() {
        return Collections.max(data.keySet());
    }

    /**
     * Gets all streams of a given artist for given weeks
     *
     * @param artist the artist whose streams will be returned
     * @param weeks the weeks of stream data that will be accessed
     * @return a hashmap of weeks to streams
     */
    public HashMap<Integer, Integer> getStreams(Artist artist, List<Integer> weeks) {
        HashMap<Integer,Integer> to_return = new HashMap<>();
        String name = artist.getName();
        for (int i: weeks) {
            to_return.put(i, getStreamsHelper(name, i));
        }

        return to_return;
    }

//    Helper method for getStreams that returns artist streams of a week
//    Assumes artists for MusicData is stored in a key(week) to value(array of artists relationship)
    private int getStreamsHelper(String name, int week) {
        List<Artist> week_data = getArtists(week);
        for (Artist artist: week_data) {
            if (artist.getName().equals(name)) {
                return artist.getStreams();
            }
        }
        return 0;
    }

//    returns list of artist objects for current week
    public ArrayList<Artist> getArtists(int week){
        return data.get(week);
    }

    /**
     *
     * @param artist An Artist Object
     */
    public void addArtist(Artist artist) {
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
    public List<Artist> getArtistsByGenre(String genre){
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
    public List<String> getGenres(){
        int week = getLatestWeek();
        List<String> all_genres = new ArrayList<>();
        ArrayList<Artist> w_data = getArtists(week);

        for (Artist a : w_data){all_genres.add(a.getGenre()); }

        return all_genres.stream().distinct().collect(Collectors.toList());
    }


    /**
     * Gets the top artists of the week by streams
     *
     * @param week the current week for which we want the top artist
     * @param amount how many top Artists are we looking for
     * @return List of Artists who had the top streams of the week
     */
    public List<Artist> getTop(int week, int amount){
        ArrayList<Artist> w_data = getArtists(week);
        List<Artist> top = new ArrayList<>();

        List<Integer> streams = new ArrayList<>();
        Collections.sort(streams);

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

}
