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
     * @param user is the list of all artists that in trigger, their streams are compared and the top ones
     *                will be determined.
     */
    public void trigger(RegisteredUser user){
        List<Artist> follows = user.getFollows();
        for (Artist i: follows){
            List<Integer> weeks = Arrays.asList(MusicData.getLatestWeek() - 1, MusicData.getLatestWeek());

            HashMap<Integer, Integer> stream_week = MusicData.getStreams(i, weeks);
            if(stream_week.get(MusicData.getLatestWeek() - 1) * growth_rate <=
                    stream_week.get(MusicData.getLatestWeek())) {
                if(! this.tops.contains(i.getName())){
                    this.tops.add(i.getName());
                }

            }
        }
    }
    public void sendAlert(){
        this.message = this.tops.get(this.tops.size() - 1) + "is now on fire";
    }

    /**
     * this method returns a list of top artists names
     * @return
     */
    public ArrayList<String> showtops(){
        return this.tops;
    }
}
