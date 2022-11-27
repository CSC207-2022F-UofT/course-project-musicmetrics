import entities.GuestUser;
import entities.RegisteredUser;
import interface_adapters.Alert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RegisteredUserTest {
    RegisteredUser u = new RegisteredUser("mno@gmail.com", "password");

    @Test
    public void testGetAlerts() {
    }

    @Test
    public void testSetAlerts() {
    }

    @Test
    public void testToString() {
        Assertions.assertEquals(u.toString(), "mno@gmail.com");
    }

    @Test
    public void testDeleteAlert() {
    }

    @Test
    public void testUpdateAlert() {
    }

    @Test
    public void testCheckPermissions() {
    }

    @Test
    public void testLogOut() {
        try {
            assert (u.logout() instanceof GuestUser);
        } catch (Exception e) {
            System.out.println("Unsuccessful logout.");
        }
    }

    @Test
    public void testGetEmailAndPassword() {
        assert (u.getEmail().equals("mno@gmail.com"));
        assert (u.getPassword().equals("password"));
    }

    @Test
    public void testChangePassword() {
        String newPassword = "password2";
        u.changePassword(newPassword);
        assert (u.getPassword().equals(newPassword));
    }

    @Test
    public void testSetFollow() {
    }

    @Test
    public void testAddFollows(){
    }

    @Test
    public void testRemoveFollows(){
    }

    @Test
    public void testGetFollows(){
    }
}
