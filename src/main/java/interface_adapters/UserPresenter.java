package interface_adapters;
import use_cases.*;


public class UserPresenter {

    /**
     * Checks if the user is a GuestUser
     */
    public static boolean checkIfGuestUser(UserData userData){
        return !userData.isLoggedIn();
    }
}
