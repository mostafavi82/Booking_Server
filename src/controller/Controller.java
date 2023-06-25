package src.controller;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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

    public static JSONObject controller(String requestType, String requestData , ObjectMapper objectMapper){
        switch (requestType){
            case "createUser":
                System.out.println("in create user");
                addUserToFile(requestData,objectMapper);
                break;
            default:
                System.out.println("default in switch case");
        }
        return null;
    }

    public static ObjectNode addUserToFile(String requestData , ObjectMapper objectMapper) {
        final String USERS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\users.json";

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

            // خواندن فایل JSON و تبدیل به ArrayNode
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(USERS_FILE_PATH);
            ArrayNode arrayNode = (ArrayNode) mapper.readTree(jsonFile);

            // ایجاد ObjectNode جدید و اضافه کردن به ArrayNode
            ObjectNode newObjectNode = newUser.toObjectNode();


            arrayNode.add(newObjectNode);

            // ذخیره تغییرات در فایل JSON
            mapper.writeValue(jsonFile, arrayNode);

            System.out.println("ObjectNode added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

            // Do something with the ticket data
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode jsonResult = objectMapper.createObjectNode();
//
//        try {
//            // Read existing users from the file
//            String fileContent = new String(Files.readAllBytes(Paths.get(USERS_FILE_PATH)));
//            JsonNode usersNode = objectMapper.readTree(fileContent);
//            System.out.println("get file content");
//
//            if (hasUser(userJson , usersNode)) {
//                jsonResult.put("result", false);
//                System.out.println("result is false");
//            } else {
//                System.out.println("in else");
//                // Add the user to the array
//                ((ObjectNode) usersNode).putPOJO("user", userJson);
//                System.out.println("create ObjectNode");
//                // Write the updated JSON back to the file
//                objectMapper.writeValue(new File(USERS_FILE_PATH), usersNode);
//
//                jsonResult.put("result", true);
//                System.out.println("User added successfully to users.json");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error adding user to users.json");
//            jsonResult.put("result", false);
//        } catch (Exception e) {
//            jsonResult.put("result", false);
//        } finally {
//            return jsonResult;
//        }
        return null;
    }





    public static boolean hasUser(String userJson, JsonNode usersArray) {
        System.out.println("in hasUser");
//        ObjectNode newUserJson = (ObjectNode) userJson;
//
//        for (JsonNode existingUser : usersArray) {
//            if (existingUser.equals(newUserJson)) {
//                System.out.println("\n \n------------------equals------------------ \n \n");
//                return true;
//            }
//        }
//
//        if (usersArray.size() > 0) {
//            System.out.println("\n 1. user1 : " + usersArray.get(0));
//            System.out.println("\n 2. newUser : " + userJson);
//        }
//        System.out.println("end of hasUser");
        return false;
    }







}

