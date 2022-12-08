package use_cases;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import entities.Artist;
import entities.User;

public class Alert {
    static double growth_rate = 1.25;
    HashMap<String, Float> tops;
    User user;

    public Alert(User user) {
        this.tops = new HashMap<String, Float>();
        this.user = user;
    }

    /**
     * trigger checks if the artists streams had the sufficient amount of growth compare to its last week
     * streams
     * user is the list of all artists that in trigger, their streams are compared and the top ones
     * will be determined.
     */
    public void trigger() {
        List<Artist> follows = this.user.getFollows();
        for (Artist i : follows) {
            List<Integer> weeks = Arrays.asList(MusicData.getLatestWeek() - 1, MusicData.getLatestWeek());
            HashMap<Integer, Integer> stream_week = MusicData.getStreams(i, weeks);
            float growth = (float) stream_week.get(MusicData.getLatestWeek()) / (float) stream_week.get(MusicData.getLatestWeek() - 1);
            if (stream_week.get(MusicData.getLatestWeek() - 1) * growth_rate <=
                    stream_week.get(MusicData.getLatestWeek())) {
                if (!this.tops.containsKey(i.getName())) {
                    this.tops.put(i.getName(), growth);
                }

            }
        }
    }

    public HashMap<String, Float> getTop() {
        return this.tops;
    }
}