package src.Classes;

public class Transaction {
    public String date;
    public String type;
    public int amount;
    public String id;

    public Transaction(String date, String type, int amount, String id){
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.id = id;
    }
}
