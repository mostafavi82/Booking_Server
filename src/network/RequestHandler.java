package src.network;

import com.fasterxml.jackson.databind.node.ObjectNode;
import src.Classes.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.*;

import org.json.JSONException;

import org.json.JSONObject;
import java.net.Socket;
import java.util.Scanner;

import src.Classes.User;
import src.controller.Controller;

public class RequestHandler extends Thread{
    private final Socket socket;

    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
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


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            String requestType = jsonNode.get("requestType").asText();
            String requestData = jsonNode.get("requestData").asText();
            System.out.println(requestData);


            Controller controll = new Controller();
            controll.controller(requestType, requestData,objectMapper);

            socket.close();

        }catch (IOException err) {
            err.printStackTrace();
        }

    }
    public User convertJsonToUser(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(jsonString, User.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


