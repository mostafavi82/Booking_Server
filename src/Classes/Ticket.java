package src.Classes;

public class Ticket {
    String companyName;
    String origin;
    String destination;
    String departureTime;
    String arrivalTime;
    String travelTime;
    int cost;
    String travelClass;
    int id;


    Ticket(String companyName,String origin,String destination,String departureTime,String arrivalTime,String travelTime, int cost , String travelClass,int id){
        this.companyName = companyName;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.travelTime = travelTime;
        this.cost = cost;
        this.travelClass = travelClass;
        this.id = id;
    }
}
