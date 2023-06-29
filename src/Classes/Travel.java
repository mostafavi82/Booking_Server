package src.Classes;

public class Travel {
    String companyName;
    String origin;
    String destination;
    String vehicle;
    int remainingPassengers;
    int capacity;
    String departureTime;
    String arrivalTime;
    String travelTime;
    int cost;
    String travelClass;
    int id;


    Travel(String companyName,String origin,String destination,String vehicle,int remainingPassengers ,int capacity ,String departureTime,String arrivalTime,String travelTime, int cost , String travelClass,int id){
        this.companyName = companyName;
        this.origin = origin;
        this.destination = destination;
        this.vehicle = vehicle;
        this.remainingPassengers = remainingPassengers;
        this.capacity = capacity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.travelTime = travelTime;
        this.cost = cost;
        this.travelClass = travelClass;
        this.id = id;
    }
}
