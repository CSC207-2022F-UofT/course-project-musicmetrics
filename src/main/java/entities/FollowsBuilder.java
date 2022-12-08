package entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FollowsBuilder {
    public static HashMap<String, ArrayList<String>> followsMap = new HashMap<>();
    public FollowsBuilder() throws FileNotFoundException {
        String email;

        // read follows data
        Scanner followsSc = new Scanner(new File("src/main/java/entities/Follows")).useDelimiter("\n");
        for (; followsSc.hasNext(); ) {
            String i = followsSc.next();
            String[] j = i.split(", ");
            email = j[0];
            ArrayList<String> currlst = new ArrayList<>();

            for (int k = 1; k < j.length; k++) {
                currlst.add(j[k]);
            }
            followsMap.put(email, currlst);

        }
    }
}
