package interface_adapters;
import use_cases.*;


public class UserPresenter {

    /**
     * checks if the user is a GuestUser
     */
    public static boolean checkIfGuestUser(){
        return UserInteractor.isGuestUser;
    }
}
