package use_cases;

import entities.*;

import java.util.Scanner;

public class ArtistBuilder {
    /**
     * Reads info from relevant file and stores in a new Artist instance
     *
     * @param sc Scanner passed from setData to read this line
     * @param week Current week and file data is extracted from
     *
     * @return Artist instance with current week's info stored
     */
    public static Artist setArtistData(Scanner sc, int week){
        String name, genre;
        int streams, follows;

        //read all info
        name = sc.next();
        streams = Integer.parseInt(sc.next());
        follows = Integer.parseInt(sc.next());
        genre = sc.next();
        Boolean[] likes = likesConverter(sc.next());

        return new Artist(follows, genre, name, likes, week, streams);
    }

    /**
     * converts a string of 1s and 0s into a boolean array
     *
     * @param binary a string of 1s and 0s to represent likes
     * @return boolean array corresponding to binary
     */
    private static Boolean[] likesConverter(String binary) {
        String[] binaryList = binary.split(" ");
        Boolean[] likes = new Boolean[binary.length()];
        for(int i = 0; i < binaryList.length; i++){
            String s = binaryList[i];
            likes[i] = s.equals("1");
        }
        return likes;
    }

}
