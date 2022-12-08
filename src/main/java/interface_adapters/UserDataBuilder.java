package interface_adapters;

import use_cases.*;

public class UserDataBuilder {

    private final UserData userData;

    public UserDataBuilder() throws Exception {
        this.userData = new UserData();
    }

    /** Returns UserData attribute
     *
     * @return UserData attribute
     */
    public UserData getUserData() {
        return this.userData;
    }

}
