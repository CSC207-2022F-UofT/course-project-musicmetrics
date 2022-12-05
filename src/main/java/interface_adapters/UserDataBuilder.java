package interface_adapters;

import use_cases.*;

public class UserDataBuilder {

    private UserData userData;

    public UserDataBuilder() throws Exception {
        this.userData = new UserData();
    }

    public UserData getUserData() {
        return this.userData;
    }

    public void updateUserData() throws Exception {
        userData = new UserData();
    }
}
