package src.network;

import java.net.Socket;

public class RequestHandler extends Thread{
    private Socket socket;

    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        super.run();
    }
}
