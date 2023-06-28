package src.network;

import com.fasterxml.jackson.databind.node.ObjectNode;
import src.Classes.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.*;

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
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();

            System.out.println(jsonString);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            String requestType = jsonNode.get("requestType").asText();
            String requestData = jsonNode.get("requestData").asText();
            System.out.println(requestData);


            Controller controll = new Controller();
            controll.controller(requestType, requestData,objectMapper,dos);


            reader.close();
            dos.close();
            socket.close();

        }catch (IOException err) {
            err.printStackTrace();
        }

    }

}


