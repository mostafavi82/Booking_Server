package src.controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import src.Classes.Ticket;
import src.Classes.Transaction;
import src.Classes.User;


public class Controller {
//    public Controller(String requestType, JSONObject requestData) {
//    }

    public static void controller(String requestType, String requestData , ObjectMapper objectMapper , DataOutputStream dos){

        switch (requestType){
            case "createUser":
                System.out.println("in create user");
                addUserToFile(requestData,objectMapper ,dos);
                break;

            case "checkPasswordAndUsername":
                System.out.println("in checkPasswordAndUsername");
                checkPasswordAndUsername(requestData,objectMapper ,dos);
                break;

            case "changeWalletBalanceAndCreateTransaction" :
                System.out.println("in changeWalletBalanceAndCreateTransaction");
                changeWalletBalanceAndCreateTransaction(requestData,objectMapper ,dos);
                break;

            case "changeAccountInformation" :
                System.out.println("in changeAccountInformation");
                changeAccountInformation(requestData,objectMapper ,dos);
                break;

            case "changePersonalInformation" :
                System.out.println("in changePersonalInformation");
                changePersonalInformation(requestData,objectMapper ,dos);
                break;

            case "addFutureTravelToTicketsList" :
                System.out.println("in addFutureTravelToTicketsList");
                addFutureTravelToTicketsList(requestData,objectMapper ,dos);
                break;

            case "getTicketList" :
                System.out.println("in getTicketList");
                getTicketList(requestData,objectMapper ,dos);
                break;


            default:
                System.out.println("default in switch case");

        }
    }

    public static ObjectNode addUserToFile(String requestData , ObjectMapper objectMapper , DataOutputStream dos) {
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

        //send data to client
        byte[] outputByte = result.toString().getBytes(StandardCharsets.UTF_8);
        try {
            dos.write(outputByte);
        } catch (IOException e) {
            e.printStackTrace();
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


    public static boolean checkPasswordAndUsername(String requestData , ObjectMapper objectMapper , DataOutputStream dos) {
        final String USERS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\users.json";

        ObjectMapper resultMapper = new ObjectMapper();
        ObjectNode result = resultMapper.createObjectNode();
        result.put("username" , "null");

        try {
            // Parsing the inner JSON string again
            JsonNode innerJsonNode = objectMapper.readTree(requestData);

            String username = innerJsonNode.get("username").asText();
            String password = innerJsonNode.get("password").asText();


            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(USERS_FILE_PATH);
            ArrayNode usersList = (ArrayNode) mapper.readTree(jsonFile);
            for(int i = 0; i < usersList.size() ; i++){
                if(usersList.get(i).get("username").asText().equals(username)){

                    if(usersList.get(i).get("password").asText().equals(password)){
                        result = (ObjectNode)usersList.get(i);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //send data to client
        byte[] outputByte = result.toString().getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println("returnt data is : " + result.toString());
            dos.write(outputByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }





    public static boolean changeWalletBalanceAndCreateTransaction(String requestData , ObjectMapper objectMapper , DataOutputStream dos) {
        final String USERS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\users.json";

        ObjectMapper resultMapper = new ObjectMapper();
        ObjectNode result = resultMapper.createObjectNode();
        result.put("result" , false);

        try {
            // Parsing the inner JSON string again
            JsonNode innerJsonNode = objectMapper.readTree(requestData);

            String username = innerJsonNode.get("username").asText();
            int amount = innerJsonNode.get("amount").asInt();
            String id = innerJsonNode.get("id").asText();



            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(USERS_FILE_PATH);
            ArrayNode usersList = (ArrayNode) mapper.readTree(jsonFile);
            for(int i = 0; i < usersList.size() ; i++){
                if(usersList.get(i).get("username").asText().equals(username)){

                    int walletBalance = usersList.get(i).get("walletBalance").asInt();
                    System.out.println(amount + walletBalance);
                    String newWalletBalance = String.valueOf(walletBalance + amount);

                    ((ObjectNode)usersList.get(i)).put("walletBalance" , newWalletBalance);

                    ObjectNode transactionNode = mapper.createObjectNode();
                    transactionNode.put("date", formatDate());
                    transactionNode.put("type", (amount > 0) ? "Income" : "Buy");
                    transactionNode.put("amount",(amount > 0) ? amount : -1 * amount);
                    transactionNode.put("id", id);
                    ((ObjectNode)usersList.get(i).get("transactionsList")).set(id, transactionNode);

                    ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                    writer.writeValue(jsonFile, usersList);
                    result.put("result" , true);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //send data to client
        byte[] outputByte = result.toString().getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println("returnt data is : " + result.toString());
            dos.write(outputByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean changeAccountInformation(String requestData , ObjectMapper objectMapper , DataOutputStream dos) {
        final String USERS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\users.json";

        ObjectMapper resultMapper = new ObjectMapper();
        ObjectNode result = resultMapper.createObjectNode();
        result.put("result" , false);

        try {
            // Parsing the inner JSON string again
            JsonNode innerJsonNode = objectMapper.readTree(requestData);

            String username = innerJsonNode.get("username").asText();
            String newUsername = innerJsonNode.get("newUsername").asText();
            String newPassword = innerJsonNode.get("newPassword").asText();
            String newEmail = innerJsonNode.get("newEmail").asText();



            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(USERS_FILE_PATH);
            ArrayNode usersList = (ArrayNode) mapper.readTree(jsonFile);
            for(int i = 0; i < usersList.size() ; i++){
                if(usersList.get(i).get("username").asText().equals(username)){
                    ((ObjectNode)usersList.get(i)).put("username" , newUsername);
                    ((ObjectNode)usersList.get(i)).put("password" , newPassword);
                    ((ObjectNode)usersList.get(i)).put("email" , newEmail);
                    ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                    writer.writeValue(jsonFile, usersList);
                    result.put("result" , true);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //send data to client
        byte[] outputByte = result.toString().getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println("returnt data is : " + result.toString());
            dos.write(outputByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String formatDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }


    public static boolean changePersonalInformation(String requestData , ObjectMapper objectMapper , DataOutputStream dos) {
        final String USERS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\users.json";

        ObjectMapper resultMapper = new ObjectMapper();
        ObjectNode result = resultMapper.createObjectNode();
        result.put("result" , false);

        try {
            // Parsing the inner JSON string again
            JsonNode innerJsonNode = objectMapper.readTree(requestData);

            String username = innerJsonNode.get("username").asText();
            String newPhone = innerJsonNode.get("newPhone").asText();
            String newId = innerJsonNode.get("newId").asText();
            String newBirthday = innerJsonNode.get("newBirthday").asText();



            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(USERS_FILE_PATH);
            ArrayNode usersList = (ArrayNode) mapper.readTree(jsonFile);
            for(int i = 0; i < usersList.size() ; i++){
                if(usersList.get(i).get("username").asText().equals(username)){
                    ((ObjectNode)usersList.get(i)).put("phone" , newPhone);
                    ((ObjectNode)usersList.get(i)).put("id" , newId);
                    ((ObjectNode)usersList.get(i)).put("birthday" , newBirthday);
                    ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                    writer.writeValue(jsonFile, usersList);
                    result.put("result" , true);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //send data to client
        byte[] outputByte = result.toString().getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println("returnt data is : " + result.toString());
            dos.write(outputByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }





    public static boolean addFutureTravelToTicketsList(String requestData , ObjectMapper objectMapper , DataOutputStream dos) {
        final String USERS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\users.json";

        ObjectMapper resultMapper = new ObjectMapper();
        ObjectNode result = resultMapper.createObjectNode();
        result.put("result" , false);

        try {
            // Parsing the inner JSON string again
            JsonNode innerJsonNode = objectMapper.readTree(requestData);

            String username = innerJsonNode.get("username").asText();
            String companyName = innerJsonNode.get("companyName").asText();
            String origin = innerJsonNode.get("origin").asText();
            String destination = innerJsonNode.get("destination").asText();
            String departureTime = innerJsonNode.get("departureTime").asText();
            String arrivalTime = innerJsonNode.get("arrivalTime").asText();
            String travelTime = innerJsonNode.get("travelTime").asText();
            int cost = innerJsonNode.get("cost").asInt();
            String travelClass = innerJsonNode.get("travelClass").asText();
            int id = innerJsonNode.get("id").asInt();



            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(USERS_FILE_PATH);
            ArrayNode usersList = (ArrayNode) mapper.readTree(jsonFile);
            for(int i = 0; i < usersList.size() ; i++){
                if(usersList.get(i).get("username").asText().equals(username)){


                    ObjectNode ticketNode = mapper.createObjectNode();
                    ticketNode.put("companyName", companyName);
                    ticketNode.put("origin", origin);
                    ticketNode.put("destination", destination);
                    ticketNode.put("departureTime", departureTime);
                    ticketNode.put("arrivalTime", arrivalTime);
                    ticketNode.put("travelTime", travelTime);
                    ticketNode.put("cost", cost);
                    ticketNode.put("travelClass", travelClass);
                    ticketNode.put("id", id);
                    ((ObjectNode)usersList.get(i).get("ticketsList")).set(String.valueOf(id), ticketNode);

                    ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                    writer.writeValue(jsonFile, usersList);
                    result.put("result" , true);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //send data to client
        byte[] outputByte = result.toString().getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println("returnt data is : " + result.toString());
            dos.write(outputByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




    public static boolean getTicketList(String requestData , ObjectMapper objectMapper , DataOutputStream dos) {
        final String TravelS_FILE_PATH = "C:\\Users\\Hooshmand\\IdeaProjects\\Booking_Server\\src\\database\\travels.json";

        List<String> resultList = new ArrayList<>();



        try {
            JsonNode innerJsonNode = objectMapper.readTree(requestData);
            String sort = innerJsonNode.get("sort").asText();
            String filter = innerJsonNode.get("filter").asText();
            String travel = innerJsonNode.get("travel").asText();

            String origin = "";
            String destination = "";
            String date = "" ;
            String vehicle = "";
            int passengersNumber = 0;

            System.out.println("travel : " + travel);
            // Parsing the transaction JSON string again
            JsonNode travelJsonNode = objectMapper.readTree(travel);
            origin = travelJsonNode.get("origin").asText();
            destination = travelJsonNode.get("destination").asText();
            date = travelJsonNode.get("date").asText();
            vehicle = travelJsonNode.get("vehicle").asText();
            passengersNumber = travelJsonNode.get("passengersNumber").asInt();
            System.out.println("origin : " + origin + " - destination : " + destination + " - date : " + date);


            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(TravelS_FILE_PATH);
            ArrayNode travelsList = (ArrayNode) mapper.readTree(jsonFile);
            for(int i = 0; i < travelsList.size() ; i++){
                if(travelsList.get(i).get("origin").asText().equals(origin)){
                    if(travelsList.get(i).get("destination").asText().equals(destination)){
                        if (compareDates(travelsList.get(i).get("departureTime").asText(),date)) {
                            if(travelsList.get(i).get("vehicle").asText().equals(vehicle)){
                                if(travelsList.get(i).get("remainingPassengers").asInt() >= passengersNumber){
                                    resultList.add(travelsList.get(i).toString());
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = String.join(",", resultList);
        //send data to client
        byte[] outputByte = result.getBytes(StandardCharsets.UTF_8);
        try {
            dos.write(outputByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean compareDates(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime localDateTime1 = LocalDateTime.parse(date1, formatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse(date2, formatter);

        return localDateTime1.toLocalDate().isEqual(localDateTime2.toLocalDate());
    }



}

