package interface_adapters;

import use_cases.*;

import java.util.HashMap;


public class AlertsController {

    /**
     *
     * @param email the email of the user
     * @param userData UserData

     * @return returns each top artist names and their stream growth rate in front of their names.
     */
    public static String format(String email, UserData userData, Double gr) throws Exception {
        var user = userData.getUser(email);
        Alert alert = new Alert(user);
        alert.trigger(gr);
        StringBuilder message = new StringBuilder();

        HashMap<String, Float> map = alert.getTop();

        for (String i : map.keySet()) {
            message.append(i).append(": ").append(String.format("%.2f", map.get(i))).append("x").append("\n");
        }
        if (message.length() > 0) {
            message.setLength(message.length() - 1);
        }
        return message.toString();
    }

}
