package interface_adapters;

import use_cases.*;


public class UserPresenter {

    /**
     * Checks if the user is logged in (i.e., is a GuestUser)
     *
     * @param userData UserData
     * @return a boolean whether the user is a GuestUser.
     */
    public static boolean checkIfGuestUser(UserData userData){
        return !userData.isLoggedIn();
    }
}
