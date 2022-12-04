package entities;
import java.util.List;

public class GuestUser extends entities.User {
    public GuestUser(){
    }

    public boolean checkUser(){
        return true;
    }

    public List<entities.User> getUser(){
        return null;
    }

    @Override
    public boolean checkPermissions() {
        return false;
    }

}
