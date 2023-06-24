package src.Classes;
import java.util.ArrayList;


public class User {
    String username;
    String password;
    String email;
    String avatarPath;
    String phone;
    String id;
    String birthday;
    String walletBalance;
    ArrayList<Transaction> transactionsList;
    ArrayList<Ticket> ticketsList;

    User(String username,String password,String email,String avatarPath,String phone,String id,String birthday,String walletBalance,ArrayList<Transaction> transactionsList , ArrayList<Ticket> ticketsList){
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
}
