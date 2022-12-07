package use_cases;

import entities.Artist;

import java.util.HashMap;
import java.util.List;

public interface stream {
    public static int getLatestWeek() {
        return 0;
    }

    public static HashMap<Integer, Integer> getStreams() {
        return null;
    }

    private static int getStreamsHelper() {
        return 0;
    }

    public static List<Artist> getTrending() {
        return null;
    }
}