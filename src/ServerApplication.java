import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerApplication {
    private int challengeSolution;
    private ServerSocket server;
    private Socket socket;
    private ServerChallengeInfo serverChallenge;

    public ServerApplication(int port){
        awaitConnection(port);
        serverChallenge = new ServerChallengeInfo(createChallenge());
        sendChallenge();
        awaitResponse();
        sendChallengeStatus();
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

    private String createChallenge(){
        char[] operations = new char[] {'+', '-', '*', '/'};
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        //Generate first digit
        builder.append(random.nextInt(10));
        //Generate Operation
        char operation = operations[random.nextInt(4)];
        builder.append(operation);

        //Generate Last Digit
        if (operation == '/')
            builder.append(random.nextInt(1,10));
        else
            builder.append(random.nextInt(10));
        return builder.toString();
    }
    private void sendChallenge(){
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(serverChallenge);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void awaitResponse(){
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(socket.getInputStream());
            serverChallenge = (ServerChallengeInfo) is.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
