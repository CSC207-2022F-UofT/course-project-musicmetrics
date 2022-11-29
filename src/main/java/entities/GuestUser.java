package entities;

import java.util.List;
import java.util.Scanner;

public class GuestUser extends entities.User {
    public GuestUser(){
    }

    public boolean checkUser(){
        return true;
    }

    public List<entities.User> getUser(){
        return null;
    }

    public void register() {
        // calls on userdata to save user
    }

    public void saveUser(){
    }

    public void deleteUser(){
    }

}
