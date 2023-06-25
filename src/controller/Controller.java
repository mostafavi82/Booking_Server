package src.controller;

import org.json.JSONObject;

public class Controller {
//    public Controller(String requestType, JSONObject requestData) {
//    }

    public static Controller controller(String requestType, JSONObject requestData){
        switch (requestType){
            case "createUser":
                break;
            default:
                System.out.println("default in switch case");
        }
        return null;
    }
}
