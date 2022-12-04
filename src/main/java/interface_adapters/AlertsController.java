package interface_adapters;

import use_cases.Alert;

import java.util.HashMap;


public class AlertsController {

    public StringBuilder format(Alert alert){
        StringBuilder message = new StringBuilder();
        HashMap<String, Integer> map = alert.gettop();
        for (String i: map.keySet()){
            message.append(i).append(":").append(map.get(i)).append("/n");

        }
        return message;


    }
}
