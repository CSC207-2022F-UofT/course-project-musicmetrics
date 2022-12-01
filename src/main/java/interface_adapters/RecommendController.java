package interface_adapters;

import use_cases.RecommendArtist;
import use_cases.UserInteractor;

public class RecommendController {
    /**
     * recommend with similarity
     */
    public static void similarRecommend(String genre){
        RecommendArtist.recommend(genre, true, UserInteractor.loggedinUser);
    }
    /**
     * recommend randomly
     */
    public static void randomRecommend(String genre){
        RecommendArtist.recommend(genre, false, UserInteractor.loggedinUser);
    }
}
