import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ClientHandler extends Thread{
    private float challengeSolution;
    private Socket socket;
    private MessageInfo messageHandler;

    public ClientHandler(Socket socket){
        super();
        this.socket = socket;
    }

    public void run() {
        messageHandler = new MessageInfo(createChallenge());
        challengeSolution = solveChallenge(messageHandler.getChallengeString());
        sendChallenge();
        awaitResponse();
        sendChallengeStatus();
        try {
            socket.close();
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
            oos.writeObject(messageHandler);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void awaitResponse(){
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(socket.getInputStream());
            messageHandler = (MessageInfo) is.readObject();
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
            if (messageHandler.getChallengeSolution() == challengeSolution) {
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
