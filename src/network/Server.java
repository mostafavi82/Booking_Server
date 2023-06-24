package src.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void start() throws IOException {
        try {
            ServerSocket serverSocket = serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
            }
        }catch (IOException err) {
            err.printStackTrace();
        }
    }
}
