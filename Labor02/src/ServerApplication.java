import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ServerApplication {
    private float challengeSolution;
    private ServerSocket server;
    private Socket socket;
    private ServerChallengeInfo serverChallenge;

    public ServerApplication(int port){
        awaitConnection(port);
    }
    public static void main(String[] args){
        new ServerApplication(8444);
    }

    private void awaitConnection(int port){
        try {
            server = new ServerSocket(port);
            socket = server.accept();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
