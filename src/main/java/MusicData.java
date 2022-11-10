import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.

public class MusicData{
    static HashMap<Integer, MusicData> data = new HashMap<>();

    private HashMap<Integer, MusicData> toBeDetermined  = new HashMap<>();

    public MusicData(int week){
        data.put(week, this);
    }

    public MusicData retrieveWeek(int week) {
        return data.get(week);
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
        List<Artist> week_data = data.get(week).getArtists();
        for (Artist artist: week_data) {
            if (Objects.equals(artist.getName(), name)) {
                return artist.getStreams();
            }
        }
    }

//    returns list of artist objects for current week
    public List<Artist> getArtists(){
        return null;
    }


    public void addArtist(){

    }

    /**
     * Gets all artists within a specific Genre
     *
     * @param genre gets all artists within this genre
     * @return the lists of artists within the genre
     */
    public List<Artist> getArtistsByGenre(String genre){
        return null;
    }

    public void addArtistToGenre(){
    }

    public List<String> getGenres(){
        return null;
    }

    public List<Artist> getTop(){
        return null;
    }

    public String getArtistData(){
        return null;
    }
}
