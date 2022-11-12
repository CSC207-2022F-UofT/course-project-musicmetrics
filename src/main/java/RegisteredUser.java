import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User{
    private final String email;
    private String password;
    public ArrayList<String> follows;

    public RegisteredUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.follows = new ArrayList<>();
    }

    public Alert getAlerts(double growthRate, boolean compare, int top) {
        return new Alert();
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

    public GuestUser logout() {
        UserData u = new UserData();
        return u.logoutUser(this.email, this.password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
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
