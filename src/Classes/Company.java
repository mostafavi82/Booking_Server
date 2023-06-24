package src.Classes;

public class Company {
    int id;
    String password;
    String vehicle;
    String name;
    String logoPath;

    Company(int id,String password,String vehicle,String name,String logoPath){
        this.id = id;
        this.password = password;
        this.vehicle = vehicle;
        this.name = name;
        this.logoPath = logoPath;
    }
}
