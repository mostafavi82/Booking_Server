package src.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.json.JSONObject;
import java.net.Socket;

public class RequestHandler extends Thread{
    private Socket socket;

    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        super.run();
        JSONObject json = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String jsonStr = reader.readLine();
            json = new JSONObject(jsonStr);
            reader.close();

        }catch (Exception err){
            err.printStackTrace();
            System.out.println("Error in get json from byte");
        }
        String requestType = "";
        JSONObject requestData = null;
        try{
            requestType = json.getString("requestType");
            requestData = json.getJSONObject("requestData");
        }catch (Exception err){
            err.printStackTrace();
            System.out.println("Error in Extracting requestType and requestData");
        }

        switch (requestType){
            case "createUser":
                System.out.println(requestData);
                break;
            default:
                System.out.println("default in switch case");
        }

        try {
            socket.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
