import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Alert {
    int stream;
    int stream_in_period;
    boolean hit;
    static double growth_rate = 1.25;
    ArrayList<String> tops ;

    public Alert(int stream, int stream_in_period, boolean hit){
        this.stream = stream;
        this.stream_in_period = stream_in_period;
        ArrayList<String> tops = new ArrayList<String>();


    }
    public void trigger(ArrayList<Artist> artists, int stream, int stream_in_period){
        for (Artist i: artists){
            int latest = MusicData.getLatestWeek();
            List<Integer> weeks = Arrays.asList(latest - 1, latest);

            HashMap<Integer, Integer> stream_week = MusicData.getStreams(i, weeks);
            if(stream_week.get(latest - 1) * growth_rate <= stream_week.get(latest)) {
                this.tops.add(i.getName());
            }
        }
    }
    public void sendAlert(){

    }
}
