package entities;

import java.util.ArrayList;

public abstract class User {
    public boolean checkPermissions(){
        return true;
    }

    public ArrayList<String> getFollows() {return null;};
}
