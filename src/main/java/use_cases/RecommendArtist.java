package use_cases;

import entities.*;

import java.util.List;
import java.util.Random;

public class RecommendArtist {

    /**
     * Gets an artist recommendation within a specific genre, random if similar is false, otherwise uses the similarties
     * with artists the user follows. Recommendation not within follows.
     *
     * @param genre   the genre the recommendation is within
     * @param similar if the recommendation should be based on the user's followers or random
     * @param rUser   the user that is making the request, should be registeredUser if similar is true
     * @return an artist recommended for the User
     */
    public static Artist recommend(String genre, boolean similar, User rUser) {
        Artist recommendation = new Artist();
        if (similar) {
            //generate a recommendation off of similarity scores to followed artists within the genre
            //get the User's follows and initialize the current highest similarity score (the one of the recommendation)
            List<Artist> registeredUserFollows = ((RegisteredUser) rUser).getFollows(); //similar is true means user is registered
            //get Artists not Followed within the same genre
            List<Artist> nonFollow = findNonFollows(registeredUserFollows, genre);
            //if the user follows everyone or no one return a random person they follow
            if (nonFollow.size() == 0 || registeredUserFollows.size() == 0) {
                return randomRecommend(genre);
            }
            double recommendationSimilarityScore = 0.0;
            double similarityScore = 0.0;
            for (Artist artistNotFollowed : nonFollow) {
                //for each artist within the recommended genre, generate the total similarity score to artistWithinGenre
                similarityScore = generateSimilarityScore(registeredUserFollows, artistNotFollowed);
                //generate an average similarity score and then change the recommendation if the average is greater
                //than the average of the current recommendation
                similarityScore = similarityScore / registeredUserFollows.size();
                if (similarityScore > recommendationSimilarityScore) {
                    recommendation = artistNotFollowed;
                    recommendationSimilarityScore = similarityScore;
                }
            }
        }
        else {
            recommendation = randomRecommend(genre);
        }
        return recommendation;
    }

    /**
     * Generate a recommendation randomly within the genre
     *
     * @param genre the genre the recommendation is within
     * @return an Entities.Artist object
     */
    private static Artist randomRecommend(String genre) {
        List<Artist> sameGenre = MusicData.getArtistsByGenre(genre);
        int randomInt = new Random().nextInt(sameGenre.size());
        return sameGenre.get(randomInt);
    }

    /**
     * Generate all the artists a user doesn't follow within a genre
     *
     * @param genre the genre the recommendation is within
     * @param registeredUserFollows the artists the user follows (to be filtered out if within genre)
     * @return a list of Entities.Artist objects that user does not allow
     */
    private static List<Artist> findNonFollows(List<Artist> registeredUserFollows, String genre) {
        List<Artist> sameGenre = MusicData.getArtistsByGenre(genre);
        //get Artists not Followed within the same genre
        List<Artist> nonFollow = MusicData.getArtistsByGenre(genre);
        for (Artist artistWithinGenre : sameGenre) { //filter out the ones followed
            for (Artist followed : registeredUserFollows) {
                if (followed.getName().equals(artistWithinGenre.getName())) {
                    nonFollow.remove(artistWithinGenre);
                    break;
                }
            }
        }
        return nonFollow;
    }

    /**
     * Generate a similarity score between the artist and the user's follows
     *
     * @param artistNotFollowed the artist that the similarity score should be generated for
     * @param registeredUserFollows the artists the user follows (to be compared with)
     * @return double representing similarity score between the artist and user's other followed artists
     */
    private static double generateSimilarityScore(List<Artist> registeredUserFollows, Artist artistNotFollowed) {
        double similarityScore = 0.0;
        Boolean[] artistSimilarties = artistNotFollowed.getLikes();
        for(Artist follow : registeredUserFollows){
            Boolean[] followSimilarities = follow.getLikes();
            for(int i = 0; i < artistSimilarties.length && i < followSimilarities.length; i++){
                if(followSimilarities[i] == artistSimilarties[i]){
                    similarityScore = similarityScore + 1;
                }
            }
        }
        return similarityScore;
    }
}