package use_cases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import entities.RegisteredUser;
import entities.Artist;
import interface_adapters.AlertsController;

public class Alert {
    String message;
    static double growth_rate = 1.25;
    HashMap<String, Integer> tops;
    RegisteredUser user;

    public Alert(RegisteredUser user) {
        HashMap<String, Integer> tops = new HashMap<String, Integer>();
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
            int growth = stream_week.get(MusicData.getLatestWeek()) / stream_week.get(MusicData.getLatestWeek() - 1);
            if (stream_week.get(MusicData.getLatestWeek() - 1) * growth_rate <=
                    stream_week.get(MusicData.getLatestWeek())) {
                if (!this.tops.containsKey(i.getName())) {
                    this.tops.put(i.getName(), growth);
                }

            }
        }
    }

    public HashMap<String, Integer> gettop() {return tops;}
}