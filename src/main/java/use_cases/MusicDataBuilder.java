package use_cases;


import entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static use_cases.ArtistBuilder.setArtistData;

/*
Builder to construct MusicData instances
 */
public class MusicDataBuilder {

    /**
     * reads from relevant data files and stores info in data Hashmap
     *
     * @throws FileNotFoundException for Scanner sc
     */
    public static void setData() throws FileNotFoundException {
        for (int week=1; week<=3; week++) {
            Scanner sc = new Scanner(new File("src/main/java/use_cases/music_database/Data_" + week));
            sc.useDelimiter(", ");
            sc.nextLine();
            ArrayList<Artist> allArtists = new ArrayList<>();
            while (sc.hasNextLine()) {
                Artist a = setArtistData(sc, week);
                allArtists.add(a); //add Artist to allArtists arraylist
            }
            MusicData.data.put(week, allArtists); //store information in data HashMap
            sc.close();
        }
    }
}
