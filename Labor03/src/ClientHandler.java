import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;

    public ClientHandler(Socket socket){
        super();
        this.socket = socket;
    }

    public void run() {
        sendMessage(new MessageInfo("Hello. I am ELIZA. What is your name?"));
        MessageInfo messageHandler = acceptUserResponse();
        String username = identifyUsername(messageHandler.getResponseMessage());
        sendMessage(new MessageInfo("Hello " + username + ", how are you today?"));
        messageHandler = acceptUserResponse();
        identifyWellBeing(messageHandler.getResponseMessage());
        try {
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void sendMessage(MessageInfo messageHandler){
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(messageHandler);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private MessageInfo acceptUserResponse(){
        ObjectInputStream ois;
        MessageInfo messageHandler = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            messageHandler = (MessageInfo) ois.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return messageHandler;
    }

    private String identifyUsername(String userResponse){
        String [] words = userResponse.replaceAll("[^a-zA-Z0-9]", " ").split(" ");
        return words[words.length - 1];
    }


}
