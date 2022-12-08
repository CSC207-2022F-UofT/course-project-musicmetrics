package entities;

import java.util.List;

public abstract class User {
    public boolean checkPermissions(){
        return true;
    }

    public List<Artist> getFollows() {return null;};
}
