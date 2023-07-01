package src.Classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Company {
    int id;
    String password;
    String vehicle;
    String name;
    String logoPath;

    public Company(String id, String password, String vehicle, String name, String logoPath){
        this.id = Integer.parseInt(id);
        this.password = password;
        this.vehicle = vehicle;
        this.name = name;
        this.logoPath = logoPath;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode userNode = mapper.createObjectNode();

        userNode.put("id", id);
        userNode.put("password", password);
        userNode.put("vehicle", vehicle);
        userNode.put("name", name);
        userNode.put("logoPath", logoPath);




        return userNode;
    }
}
