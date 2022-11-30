package interface_adapters;
import use_cases.MusicDataBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Searcher {

    private List<String> actions = new ArrayList<>();
    private List<String> artists = new ArrayList<>();
    private List<String> genres = new ArrayList<>();

    /**
     * Creates a new Searcher instance consisting of every possible action that the User can take.
     */
    public Searcher() throws FileNotFoundException {
        Scanner actionScanner = new Scanner(new File("src/main/java/interface_adapters/searcher_database/actions"));
        while (actionScanner.hasNextLine()) {
            this.actions.add(actionScanner.nextLine());
        }
        Scanner artistScanner = new Scanner(new File("src/main/java/interface_adapters/searcher_database/artists"));
        while (artistScanner.hasNextLine()) {
            this.artists.add(artistScanner.nextLine());
        }
        Scanner genreScanner = new Scanner(new File("src/main/java/interface_adapters/searcher_database/genres"));
        while (genreScanner.hasNextLine()) {
            this.genres.add(genreScanner.nextLine());
        }
        MusicDataBuilder.setData();
    }

    /**
     * Returns an ArrayList of at most 10 most relevant actions that the User can take based on the given keyword.
     *
     * @param keyword the search keyword that the User provided
     * @return an ArrayList of at most 10 suggestions
     */
    public List<String> filterKeyword(String keyword) {
        Map<String, Double> scoreMap = new HashMap<>();
        for (String action : this.actions) {
            double score = getRelevantScore(keyword, action);
            if (score > 0) {
                scoreMap.put(action, score);
            }
        }
        return getKeyListSortedByValue(scoreMap);
    }


    /**
     * Returns an ArrayList of at most 10 most relevant Artist that the User can navigate based on the given keyword.
     *
     * @param keyword the search keyword that the User provided
     * @return an ArrayList of at most 10 suggestions
     */
    public List<String> filterArtist(String keyword) {
        Map<String, Double> scoreMap = new HashMap<>();
        for (String artist : this.artists) {
            double score = getRelevantScore(keyword, artist);
            if (score > 0) {
                scoreMap.put(artist, score);
            }
        }
        return getKeyListSortedByValue(scoreMap);
    }



    /**
     * Returns an ArrayList of at most 10 most relevant Genre that the User can navigate based on the given keyword.
     *
     * @param keyword the search keyword that the User provided
     * @return an ArrayList of at most 10 suggestions
     */
    public List<String> filterGenre(String keyword) {
        Map<String, Double> scoreMap = new HashMap<>();
        for (String genre : this.genres) {
            double score = getRelevantScore(keyword, genre);
            if (score > 0) {
                scoreMap.put(genre, score);
            }
        }
        return getKeyListSortedByValue(scoreMap);

    }



    /**
     * Returns relevant score which indicates how relevant the keyword is to the str.
     * Returns 0 if there is no relativity (i.e., the keyword is not in str) or the keyword is longer than str.
     * If the keyword is in the given str, the score is evaluated depending on the index of the keyword in given str.
     *
     * @param keyword relativity keyword
     * @param str the String being compared to keyword to find relativity
     * @return a double relevant score (0 <= returnValue <= 100.0)
     */
    public static double getRelevantScore(String keyword, String str) {
        if (str.length() > keyword.length() && str.toLowerCase().contains(keyword.toLowerCase())) {
            return 100.0 / ((str.toLowerCase().indexOf(keyword.toLowerCase()) * 100.0) + str.length());
        }
        return 0.0;
    }

    /**
     * Returns an ArrayList of keys of the given map sorted by values of the map.
     *
     * @param map a map contains generic type as its key and double as its value
     * @return an ArrayList of keys of the map sorted by values
     */
    public static <T> List<T> getKeyListSortedByValue(Map<T, Double> map) {
        List<T> sortedList = new ArrayList<>();
        if (map.size() <= 1) {
            sortedList.addAll(map.keySet());
            return sortedList;
        }
        List<Map.Entry<T, Double>> entries = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList());
        for (Map.Entry<T, Double> entry : entries) {
            if (sortedList.size() < 10) {
                sortedList.add(entry.getKey());
            }
            else {
                break;
            }
        }
        return sortedList;
    }
}

