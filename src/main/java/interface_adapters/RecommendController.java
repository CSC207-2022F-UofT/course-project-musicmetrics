package interface_adapters;

import use_cases.RecommendArtist;
import use_cases.UserData;

public class RecommendController {
    /**
     * recommend with similarity
     *
     * @param genre the genre the recommendation is within
     */
    public static String similarRecommend(String genre){
        return RecommendArtist.recommend(genre, true, UserData.currentUser).getName();
    }
    /**
     * recommend randomly
     *
     * @param genre the genre the recommendation is within
     */
    public static String randomRecommend(String genre){
        return RecommendArtist.recommend(genre, false, UserData.currentUser).getName();
    }
}
