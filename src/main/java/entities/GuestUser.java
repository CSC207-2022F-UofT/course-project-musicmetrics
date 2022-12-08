package entities;
import java.util.List;

public class GuestUser extends entities.User {

    public GuestUser() {

    }

    public List<entities.User> getUser(){
        return null;
    }

    /** Checks whether the user has the permission (i.e., follow permission)
     *
     * @return a boolean whether the User has the permission
     */
    @Override
    public boolean checkPermissions() {
        return false;
    }

}
