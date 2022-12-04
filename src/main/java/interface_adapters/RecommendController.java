package interface_adapters;

import use_cases.RecommendArtist;
import use_cases.UserInteractor;

public class RecommendController {
    /**
     * recommend with similarity
     *
     * @param genre the genre the recommendation is within
     */
    public static String similarRecommend(String genre){
        return RecommendArtist.recommend(genre, true, UserInteractor.loggedinUser).getName();
    }
    /**
     * recommend randomly
     *
     * @param genre the genre the recommendation is within
     */
    public static String randomRecommend(String genre){
        return RecommendArtist.recommend(genre, false, UserInteractor.loggedinUser).getName();
    }
}
