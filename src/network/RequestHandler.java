package src.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler extends Thread{
    private Socket socket;

    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        super.run();
        System.out.println("in RequestHandler");

        String jsonString = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonString = stringBuilder.toString();

        }catch (Exception err){
            err.printStackTrace();
            System.out.println("Error in get json from byte");
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestType = "";
        String requestDataString = null;
        try{

            requestType = jsonObject.getString("requestType");
            requestDataString = jsonObject.getString("requestData");
            System.out.println("Extract json");
        }catch (Exception err){
            err.printStackTrace();
            System.out.println("Error in Extracting requestType and requestData");
        }
        JSONObject requestData = null;
        try {
            requestData = new JSONObject(requestDataString);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error in creating json from requestDataString");
        }
        switch (requestType){
            case "createUser":
                System.out.println(requestDataString);
                break;
            default:
                System.out.println("default in switch case");
        }

        try {
            socket.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
