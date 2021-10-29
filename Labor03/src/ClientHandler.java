import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private float challengeSolution;
    private Socket socket;
    private MessageInfo messageHandler;
    private final String WELCOME_MESSAGE = "Hello. I am ELIZA. What is your name?";

    public ClientHandler(Socket socket){
        super();
        this.socket = socket;
    }

    public void run() {
        messageHandler = new MessageInfo(WELCOME_MESSAGE);
        sendWelcomeMessage();
        awaitResponse();
        try {
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void sendWelcomeMessage(){
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

}
