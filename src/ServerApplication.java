import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
    private int challengeSolution;
    private ServerSocket server;
    private Socket client;

    public ServerApplication(){
        awaitConnection();
        sendChallenge();
        awaitResponse();
        sendChallengeStatus();
    }
    public static void main(String[] args){
        new ServerApplication();
    }

    private void awaitConnection(){
        try {
            server = new ServerSocket(8444);
            client = server.accept();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
