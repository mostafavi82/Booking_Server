package src.Classes;

public class Transaction {
    String date;
    String type;
    int amount;
    String id;

    Transaction(String date , String type,int amount,String id){
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.id = id;
    }
}
