package use_cases;

import use_cases.*;

public class UserDataBuilder {

    private UserData userData;

    public UserDataBuilder() throws Exception {
        this.userData = new UserData();
    }

    public UserData getUserData() {
        return this.userData;
    }
}
