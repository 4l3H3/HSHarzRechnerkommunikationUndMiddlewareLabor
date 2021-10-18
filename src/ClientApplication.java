import java.io.IOException;
import java.net.Socket;

public class ClientApplication {
    private Socket socket;
    public ClientApplication(int port){
        connectToServer(port);
        awaitChallenge();
        solveChallenge();
        sendSolvedChallenge();
        readResponse();
    }
    public static void main(String[] args){
        new ClientApplication(8444);
    }

    private void connectToServer(int port){
        try {
            socket = new Socket("localhost",port);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    



}
