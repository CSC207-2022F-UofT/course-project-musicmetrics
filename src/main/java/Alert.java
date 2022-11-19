import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Alert {
    String message;
    static double growth_rate = 1.25;
    ArrayList<String> tops ;

    public Alert(){
        ArrayList<String> tops = new ArrayList<String>();


    }

    /** trigger checks if the artists streams had the sufficient amount of growth compare to its last week
     * streams
     * @param artists is the list of all artists that in trigger, their streams are compared and the top ones
     *                will be determined.
     */
    public void trigger(ArrayList<Artist> artists){
        for (Artist i: artists){
            List<Integer> weeks = Arrays.asList(MusicData.getLatestWeek() - 1, MusicData.getLatestWeek());

            HashMap<Integer, Integer> stream_week = MusicData.getStreams(i, weeks);
            if(stream_week.get(MusicData.getLatestWeek() - 1) * growth_rate <= stream_week.get(MusicData.getLatestWeek())) {
                this.tops.add(i.getName());
            }
        }
    }
    public void sendAlert(){
        this.message = this.tops.get(this.tops.size() - 1) + "is now on fire";
    }
}
