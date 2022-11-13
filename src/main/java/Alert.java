import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Alert {
    int stream;
    static double growth_rate = 1.25;
    ArrayList<String> tops ;

    public Alert(){
        ArrayList<String> tops = new ArrayList<String>();


    }
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
        String message = this.tops.get(this.tops.size() - 1) + "is now on fire";
    }
}
