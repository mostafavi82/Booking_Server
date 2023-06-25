package src.Classes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;


public class User {
    public String username;
    public String password;
    public String email;
    public String avatarPath;
    public String phone;
    public String id;
    public String birthday;
    public String walletBalance;
    public ArrayList<Transaction> transactionsList;
    public ArrayList<Ticket> ticketsList;

    public User(String username, String password, String email, String avatarPath, String phone, String id, String birthday, String walletBalance, ArrayList<Transaction> transactionsList, ArrayList<Ticket> ticketsList){
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatarPath = avatarPath;
        this.phone = phone;
        this.id = id;
        this.birthday = birthday;
        this.walletBalance = walletBalance;
        this.transactionsList = transactionsList;
        this.ticketsList = ticketsList;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode userNode = mapper.createObjectNode();

        userNode.put("username", username);
        userNode.put("password", password);
        userNode.put("email", email);
        userNode.put("avatarPath", avatarPath);
        userNode.put("phone", phone);
        userNode.put("id", id);
        userNode.put("birthday", birthday);
        userNode.put("walletBalance", walletBalance);

        // تبدیل لیست transactionsList به ObjectNode
        ObjectNode transactionsListNode = mapper.createObjectNode();
        for (Transaction transaction : transactionsList) {
            ObjectNode transactionNode = mapper.createObjectNode();
            transactionNode.put("date", transaction.date);
            transactionNode.put("type", transaction.type);
            transactionNode.put("amount", transaction.amount);
            transactionNode.put("id", transaction.id);
            transactionsListNode.set(transaction.id, transactionNode);
        }
        userNode.set("transactionsList", transactionsListNode);

        // تبدیل لیست ticketsList به ObjectNode
        ObjectNode ticketsListNode = mapper.createObjectNode();
        for (Ticket ticket : ticketsList) {
            ObjectNode ticketNode = mapper.createObjectNode();
            ticketNode.put("companyName", ticket.companyName);
            ticketNode.put("origin", ticket.origin);
            ticketNode.put("destination", ticket.destination);
            ticketNode.put("departureTime", ticket.departureTime);
            ticketNode.put("arrivalTime", ticket.arrivalTime);
            ticketNode.put("travelTime", ticket.travelTime);
            ticketNode.put("cost", ticket.cost);
            ticketNode.put("travelClass", ticket.travelClass);
            ticketNode.put("id", ticket.id);
            ticketsListNode.set(String.valueOf(ticket.id), ticketNode);
        }
        userNode.set("ticketsList", ticketsListNode);

        return userNode;
    }
}
