import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientApplication {
    private Socket socket;
    private ServerChallengeInfo serverChallengeInfo;
    public ClientApplication(int port){
        connectToServer(port);
        readServerChallenge();
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

    private void readServerChallenge(){
        ObjectInputStream iis;
        try {
            iis = new ObjectInputStream(socket.getInputStream());
            serverChallengeInfo = (ServerChallengeInfo) iis.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


}
