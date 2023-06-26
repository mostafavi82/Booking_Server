package src.controller;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import src.Classes.*;

import java.io.File;
import java.util.ArrayList;


public class Controller {
//    public Controller(String requestType, JSONObject requestData) {
//    }

    public static ObjectNode controller(String requestType, String requestData , ObjectMapper objectMapper){

        switch (requestType){
            case "createUser":
                System.out.println("in create user");
                return addUserToFile(requestData,objectMapper);
            default:
                System.out.println("default in switch case");
                return null;
        }
    }

    public static ObjectNode addUserToFile(String requestData , ObjectMapper objectMapper) {
        final String USERS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\users.json";

        ObjectMapper resultMapper = new ObjectMapper();
        ObjectNode result = resultMapper.createObjectNode();

        try {
            // Parsing the inner JSON string again
            JsonNode innerJsonNode = objectMapper.readTree(requestData);

            String username = innerJsonNode.get("username").asText();
            String password = innerJsonNode.get("password").asText();
            String email = innerJsonNode.get("email").asText();
            String avatarPath = innerJsonNode.get("avatarPath").asText();
            String phone = innerJsonNode.get("phone").asText();
            String id = innerJsonNode.get("id").asText();
            String birthday = innerJsonNode.get("birthday").asText();
            String walletBalance = innerJsonNode.get("walletBalance").asText();

            ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
            // Accessing the transactionsList array
            JsonNode transactionsList = innerJsonNode.get("transactionsList");
            for (JsonNode transactionNode : transactionsList) {
                String transaction = transactionNode.asText();
                // Parsing the transaction JSON string again
                JsonNode transactionJsonNode = objectMapper.readTree(transaction);

                String date = transactionJsonNode.get("date").asText();
                String type = transactionJsonNode.get("type").asText();
                int amount = transactionJsonNode.get("amount").asInt();
                String transactionId = transactionJsonNode.get("id").asText();

                transactionList.add(new Transaction(date,type,amount,transactionId));
            }

            // Accessing the ticketsList array
            ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
            JsonNode ticketsList = innerJsonNode.get("ticketsList");
            for (JsonNode ticketNode : ticketsList) {
                String ticket = ticketNode.asText();
                // Parsing the ticket JSON string again
                JsonNode ticketJsonNode = objectMapper.readTree(ticket);

                String companyName = ticketJsonNode.get("companyName").asText();
                String origin = ticketJsonNode.get("origin").asText();
                String destination = ticketJsonNode.get("destination").asText();
                String departureTime = ticketJsonNode.get("departureTime").asText();
                String arrivalTime = ticketJsonNode.get("arrivalTime").asText();
                String travelTime = ticketJsonNode.get("travelTime").asText();
                int cost = ticketJsonNode.get("cost").asInt();
                String travelClass = ticketJsonNode.get("travelClass").asText();
                int ticketId = ticketJsonNode.get("id").asInt();

                ticketList.add(new Ticket(companyName,origin,destination,departureTime,arrivalTime,travelTime,cost,travelClass,ticketId));
            }

            User newUser = new User(username,password,email,avatarPath,phone,id,birthday,walletBalance,transactionList,ticketList);
            System.out.println("user object created");

            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(USERS_FILE_PATH);
            ArrayNode arrayNode = (ArrayNode) mapper.readTree(jsonFile);
            ObjectNode newObjectNode = newUser.toObjectNode();

            if(!hasUser(newObjectNode,arrayNode)){
                arrayNode.add(newObjectNode);
                ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                writer.writeValue(jsonFile, arrayNode);
                System.out.println("ObjectNode added successfully.");
                result.put("result" , true);
            }else{
                System.out.println("user is exist in users.json");
                result.put("result" , false);
            }

        } catch (IOException e) {
            e.printStackTrace();
            result.put("result" , false);
        }


        return result;
    }





    public static boolean hasUser(ObjectNode newUser,ArrayNode usersList) {
        System.out.println("in hasUser");

        for(int i = 0; i < usersList.size() ; i++){
            if(usersList.get(i).get("username").equals(newUser.get("username"))){
                return true;
            }
        }
        return false;
    }







}

