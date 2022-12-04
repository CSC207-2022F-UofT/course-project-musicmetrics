package use_cases;
import entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserInteractor {
    // loggedInUser will start as a GuestUser until user decides to log into their account
    public static User loggedinUser = new GuestUser();
    public static boolean isGuestUser = true;

}
