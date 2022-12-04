package interface_adapters;

import use_cases.RecommendArtist;
import use_cases.UserInteractor;

public class RecommendController {
    /**
     * Recommends an artist with genre similar to the one given
     * @param genre string that describes the genre that the user wants an artist recommendation from
     */
    public static void similarRecommend(String genre){
        RecommendArtist.recommend(genre, true, UserInteractor.loggedinUser);
    }
    /**
     * Recommends an artist with a random genre
     * @param genre string
     */
    public static void randomRecommend(String genre){
        RecommendArtist.recommend(genre, false, UserInteractor.loggedinUser);
    }
}
