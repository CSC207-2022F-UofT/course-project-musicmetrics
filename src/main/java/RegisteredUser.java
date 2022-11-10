import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User{
    public RegisteredUser() {

    }

    public Alert getAlerts(double growthRate, boolean compare, int top) {
        return null;
    }

    public void setAlerts() {

    }

    public void deleteAlert() {

    }

    public void updateAlert(Alert alert) {

    }

    @Override
    public boolean checkPermissions() {
        return true;
    }

    public void logout() {

    }

    public void changePassword() {

    }

    public void setFollows() {

    }

    /**
     * Gets all artists a registeredUser follows
     *
     *
     * @return a List of artists the registeredUser follows
     */
    public List<Artist> getFollows() {
        return new ArrayList<Artist>();
    }
}
