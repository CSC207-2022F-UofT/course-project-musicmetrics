public class Artist {
    private int follows;
    private String genre;
    private String name;
    private Boolean[] likes;
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
     */
    public Artist(int f, String g, String n, Boolean[] l) {
        this.follows = f;
        this.genre = g;
        this.name = n;
        this.likes = l;
    }
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
}
