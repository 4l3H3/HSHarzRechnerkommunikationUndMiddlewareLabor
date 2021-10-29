import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
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
        acceptUserResponse();
        identifyUsername(messageHandler.getResponseMessage());
        sendWellBeingMessage();
        acceptUserResponse();
        sendResponse();
        sendGoodbyeMessage;
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
    private void acceptUserResponse(){
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            messageHandler = (MessageInfo) ois.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private String identifyUsername(String userResponse){
        String [] words = userResponse.replaceAll("[^a-zA-Z0-9]", " ").split(" ");
        return words[words.length - 1];
    }

}
