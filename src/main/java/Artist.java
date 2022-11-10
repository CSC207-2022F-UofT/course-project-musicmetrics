import java.util.HashMap;

public class Artist {
    private int follows;
    private String genre;
    private String name;
    private Boolean[] likes;
    private int streams;
    public int time;

    /**
     *Creates a new Artist object with everything blank
     */
    public Artist() {

    }

    /**
     *Creates a new Artist object with the follows, genre and likes.
     *
     * @param f The follows of the artist
     * @param g The genre of the artist
     * @param n The name of the artist
     * @param l The list of if user's like the artist
     * @param week An integer containing the week number
     *             ex: if this artist's data is for week 3, then week=3
     */
    public Artist(int f, String g, String n, Boolean[] l, int week, int streams) {
        this.follows = f;
        this.genre = g;
        this.name = n;
        this.likes = l;
        this.time = week;
        this.streams = streams;
    }
    public int getStreams() {return this.streams;}

    public String getName() {return this.name;}
    public int getFollows() {
        return this.follows;
    }

    public String getGenre(){
        return this.genre;
    }

    /**
     * Gets a list of booleans representing if each user likes the artist
     *
     * @return a boolean list representing if the artist is liked by each user
     */
    public Boolean[] getLikes(){
        return this.likes;
    }

    public int getTimeStamp() { return this.time;}

    public String toString(){
        return this.name;
    }

    /**
     *
     * @param artistTwo     An artist's name
     * @return      A HashMap with artist name as keys
     *
     * I assumed that MusicData.getArtistData() receives a String artist name as the input
     *  and returns a string of data.   NOTE: It may be better to have getArtistData return an ArrayList of data
     */
    public HashMap<String, Object> Compare(String artistTwo) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(this.toString(), MusicData.getArtistData(this.toString()));
        data.put(artistTwo, MusicData.getArtistData(artistTwo));
        return data;
    }
}
