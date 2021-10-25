import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ElizaServerApplication {
    private float challengeSolution;
    private ServerSocket server;
    private Socket socket;
    private MessageInfo message;
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    public ElizaServerApplication(int port){
        try {
            server = new ServerSocket(port);
        } catch (IOException e){
            e.printStackTrace();
        }
        while (true){
            awaitConnection(port);
            ClientHandler handler = new ClientHandler(socket);
            executor.execute(handler);
        }
    }
    public static void main(String[] args){
        new ElizaServerApplication(8444);
    }

    private void awaitConnection(int port){
        try {
            socket = server.accept();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
