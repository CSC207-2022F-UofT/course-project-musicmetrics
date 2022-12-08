package interface_adapters;

import use_cases.Alert;
import use_cases.UserData;

import java.util.HashMap;


public class AlertsController {
    /**
     *
     * @param alert the alert for a user.
     * @return returns each top artist names and their stream growth rate in front of their names.
     */

    public static String format(String email, UserData userData) throws Exception {
        var user = userData.getUser(email);
        Alert alert = new Alert(user);
        alert.trigger();
        StringBuilder message = new StringBuilder();
        HashMap<String, Integer> map = alert.gettop();
        for (String i: map.keySet()){
            message.append(i).append(":").append(map.get(i)).append("/n");

        }
        return message.toString();
    }

    public static void main(String[] args) throws Exception {
        UserData u = new UserData();
        System.out.println(format("abc@gmail.com", u) );
    }
}
