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
        return null;
    }

    public void setAlerts() {

    }

    public String toString() { return this.email;}

    public void deleteAlert() {

    }

    public void updateAlert(Alert alert) {

    }

    @Override
    public boolean checkPermissions() {
        return true;
    }

    /**
     * UserData's logout method is used
     * @return a new GuestUser instance
     * throws Exception if user is not logged in or does not exist in database
     */
    public GuestUser logout() throws Exception {
        UserData u = new UserData();
        return u.logoutUser(this.email, this.password);
    }

    /**
     * @param newPassword String that will be the new password
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * @return password String of RegisteredUser
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return email String of RegisteredUser
     */
    public String getEmail() {
        return this.email;
    }

    public void setFollows() {

    }

    /**
     * Gets all artists a registeredUser follows
     *
     * @return a List of artists the registeredUser follows
     */
    public List<Artist> getFollows() {
        return new ArrayList<Artist>();
    }
}
