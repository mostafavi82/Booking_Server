package src.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void start() throws IOException {
        try {
            int port = 8000;
            ServerSocket serverSocket = serverSocket = new ServerSocket(port);
            System.out.println("server is running on port " +  port);

            while (true) {
                System.out.println("Wait for connect ...");
                Socket socket = serverSocket.accept();
                System.out.println("a user connected !");
                new RequestHandler(socket).start();
            }
        }catch (IOException err) {
            err.printStackTrace();
            System.out.println("Error in accept server socket");
        }
    }
}
