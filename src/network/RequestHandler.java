package src.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.Socket;
import java.util.Scanner;
import src.controller.Controller;

public class RequestHandler extends Thread{
    private Socket socket;

    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        super.run();
        System.out.println("in RequestHandler");

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(jsonString);

            String requestType = jsonObject.getString("requestType");
            String requestDataString = jsonObject.getString("requestData");
            System.out.println("Extract json");

            JSONObject requestData = new JSONObject(requestDataString);

            Controller controll = new Controller();
            controll.controller(requestType, requestData);

            socket.close();
        }catch (IOException err) {
        err.printStackTrace();
        }catch (JSONException e) {
        e.printStackTrace();
        System.out.println("Error in creating json from requestDataString");
        }

    }
}


