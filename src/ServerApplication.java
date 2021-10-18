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
        serverChallenge = new ServerChallengeInfo(createChallenge());
        challengeSolution = solveChallenge(serverChallenge.getChallengeString());
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
            builder.append(random.nextInt(9) + 1);
        else
            builder.append(random.nextInt(10));
        return builder.toString();
    }

    private float solveChallenge(String serverChallenge){
        byte[] bytes = serverChallenge.getBytes(StandardCharsets.UTF_8);
        int first_digit = serverChallenge.charAt(0) - 48;
        int last_digit = serverChallenge.charAt(2) - 48;

        switch (bytes[1]){
            case '+': return first_digit + last_digit;
            case '-': return first_digit - last_digit;
            case '*': return first_digit * last_digit;
            case '/': return first_digit / last_digit;
            default: return Float.NEGATIVE_INFINITY;
        }
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

    private void sendChallengeStatus(){
        BufferedWriter oos;
        try {
            oos = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            if (serverChallenge.getChallengeSolution() == challengeSolution) {
                oos.write("The challenge has been solved. Good Job!");
            }
            else {
                oos.write("Wrong! Try connecting again.");
            }
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
