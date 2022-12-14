package entities;

import java.util.HashMap;

public class Artist {
    private int follows;
    private String genre;
    private String name;
    private Boolean[] likes;
    private int streams;
    public int time;

    /**
     *Creates a new Entities.Artist object with everything blank
     */
    public Artist() {

    }

    /**
     *Creates a new Entities.Artist object with the follows, genre and likes.
     *
     * @param follows The follows of the artist
     * @param genre The genre of the artist
     * @param name The name of the artist
     * @param l The list of if user's like the artist
     * @param week An integer containing the week number
     *             ex: if this artist's data is for week 3, then week=3
     */
    public Artist(int follows, String genre, String name, Boolean[] l, int week, int streams) {
        this.follows = follows;
        this.genre = genre;
        this.name = name;
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

    /**
     *
     * @return a HashMap displaying this artist's information
     */
    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Name", this.name);
        info.put("Genre", this.genre);
        info.put("Follows", this.follows);
        info.put("Streams", this.streams);
        info.put("TimeStamp", this.time);
        return info;
    }

    public int getTimeStamp() { return this.time;}

    public String toString() { return this.name;}


}
