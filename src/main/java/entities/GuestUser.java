package entities;

import java.util.List;

public class GuestUser extends User {
    public GuestUser(){
    }

    public boolean checkUser(){
        return true;
    }

    public List<User> getUser(){
        return null;
    }

    public void saveUser(){
    }

    public void deleteUser(){
    }

    public void logInUser() throws Exception {
    }
    public void logoutUser(){
    }
}
