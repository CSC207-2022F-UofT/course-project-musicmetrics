import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetrieveMusicData {

    private HashMap<Integer, MusicData> data = new HashMap<>();

    public void storeWeek(int week, MusicData musicData) {
        this.data.put(week, musicData);
    }

    public MusicData retrieveWeek(int week) {
        return data.get(week);
    }

    public List<Artist> getTrending(int top, int startWeek, int endWeek) {
        return new ArrayList<>();
    }

    public Artist recommendArtist(String genre, boolean similar) {
        return new Artist();
    }

    public int getStreams() {
        return 0;
    }

}
