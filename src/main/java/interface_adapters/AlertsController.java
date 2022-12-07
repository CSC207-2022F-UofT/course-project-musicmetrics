package interface_adapters;

import use_cases.Alert;

import java.util.HashMap;


public class AlertsController {
    /**
     *
     * @param alert the alert for a user.
     * @return returns each top artist names and their stream growth rate in front of their names.
     */

    public StringBuilder format(Alert alert){
        alert.trigger();
        StringBuilder message = new StringBuilder();
        HashMap<String, Integer> map = alert.gettop();
        for (String i: map.keySet()){
            message.append(i).append(":").append(map.get(i)).append("/n");

        }
        return message;


    }
}
