import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Searcher {

    private List<String> actions = new ArrayList<>();
    private List<String> artists = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private MusicData musicData = new MusicData();

    /**
     * Creates a new Searcher instance consisting of every possible action that the User can take.
     */
    public Searcher() throws FileNotFoundException {
        Scanner actionScanner = new Scanner(new File("src/main/java/database/actions"));
        while (actionScanner.hasNextLine()) {
            this.actions.add(actionScanner.nextLine());
        }
        Scanner artistScanner = new Scanner(new File("src/main/java/database/artists"));
        while (artistScanner.hasNextLine()) {
            this.artists.add(artistScanner.nextLine());
        }
        Scanner genreScanner = new Scanner(new File("src/main/java/database/genres"));
        while (genreScanner.hasNextLine()) {
            this.genres.add(genreScanner.nextLine());
        }
//        load music data
//        this.musicData.setData();
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
     * Returns an ArrayList of top n Artist where n is given in the parameter action.
     * Returns null if the keyword "top" is not found in the given action.
     *
     * @param action the action that User chose to take
     * @return an ArrayList of Artist of size n
     */
    public List<Artist> actionResult(String action) {
        if (action.startsWith("top")) {
            String[] split = action.split(" ");
            return this.musicData.getTop(MusicData.getLatestWeek(), Integer.parseInt(split[1]));
        }
        return null;
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
     * Returns an Artist whose name is the given parameter name.
     * Returns null if Artist with name is not found in the MusicData.
     *
     * @param name the name of the artist
     * @return an Artist with the given name
     */
    public Artist artistResult(String name) {
        List<Artist> artists = this.musicData.getArtists(MusicData.getLatestWeek());
        for (Artist artist : artists) {
            if (artist.getName().equals(name)) {
                return artist;
            }
        }
        return null;
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
     * Returns an ArrayList of Artists within the given genre.
     *
     * @param genre the name of the genre
     * @return an ArrayList of Artist with the given genre
     */
    public List<Artist> genreResult(String genre) {
        return musicData.getArtistsByGenre(genre);
    }

    /**
     * Returns relevant score which indicates how relevant the keyword is to the str.
     * Returns 0 if there is no relativity (i.e., the keyword is not in str) or the keyword is longer than str.
     * If the keyword is in the given str, the score is evaluated depending on the index of the keyword in given str.
     *
     * @param keyword relativity keyword
     * @param str the String being compared to keyword to find relativity
     * @return a double relevant score (0 <= returnValue <= 1.0)
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

    public static void main(String[] args) throws FileNotFoundException {
        Searcher searcher = new Searcher();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please choose search type (keyword, artist): ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("keyword")) {
            System.out.print("What do you want to search? ");
            input = scanner.nextLine();
            List<String> actions = searcher.filterKeyword(input);
            if (actions.size() == 0) {
                System.out.println("There is no action that you can take based on the provided keyword.");
            }
            else {
                for (int i = 0;i < actions.size();i++) {
                    System.out.println((i + 1) + ". " + actions.get(i));
                }
                System.out.print("Please choose one of suggestions above (provide index): ");
                try {
                    int index = Integer.parseInt(scanner.nextLine());
                    if (1 <= index && index <= actions.size()) {
                        List<Artist> artists = searcher.actionResult(actions.get(Integer.parseInt(input.strip()) - 1));
                        for (int i = 0;i < artists.size();i++) {
                            System.out.println((i + 1) + ". " + artists.get(i).getName());
                        }
                    }
                    else {
                        System.out.println("Invalid index");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index");
                }


            }
        }
        else if (input.equalsIgnoreCase("artist")) {
            System.out.print("What artist do you want to search? ");
            input = scanner.nextLine();
            List<String> artists = searcher.filterArtist(input);
            if (artists.size() == 0) {
                System.out.println("It seems like we don't have such artist.");
            }
            else {
                for (int i = 0;i < artists.size();i++) {
                    System.out.println((i + 1) + ". " + artists.get(i));
                }
                System.out.print("Please choose one of artists above you want to see (provide index): ");
                try {
                    int index = Integer.parseInt(scanner.nextLine());
                    if (1 <= index && index <= artists.size()) {
                        Artist artist = searcher.artistResult(artists.get(Integer.parseInt(input.strip()) - 1));
                        System.out.println("Name: " + artist.getName());
                        System.out.println("Genre: " + artist.getGenre());
                        System.out.println("Streams: " + artist.getStreams());
                        System.out.println("Follows: " + artist.getFollows());

                    }
                    else {
                        System.out.println("Invalid index");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index");
                }

            }
        }
        else {
            System.out.println("Invalid search type, please enter keyword or artist.");
        }
    }
}
