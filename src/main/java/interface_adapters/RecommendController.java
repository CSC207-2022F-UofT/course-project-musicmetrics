package interface_adapters;

import use_cases.*;

public class RecommendController {

    /**
     * recommend with similarity
     *
     * @param genre the genre the recommendation is within
     * @param userData UserData
     */
    public static String similarRecommend(String genre, UserData userData){
        return RecommendArtist.recommend(genre, true, userData.getCurrentUser()).getName();
    }

    /**
     * recommend randomly
     *
     * @param genre the genre the recommendation is within
     * @param userData UserData
     */
    public static String randomRecommend(String genre, UserData userData){
        return RecommendArtist.recommend(genre, false, userData.getCurrentUser()).getName();
    }
}
