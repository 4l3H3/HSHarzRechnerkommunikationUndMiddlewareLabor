import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends Thread{
    private Socket socket;
    private Map<String,String> responses = new HashMap<>();
    public ClientHandler(Socket socket){
        super();
        this.socket = socket;
    }

    public void run() {
        sendMessage(new MessageInfo("Hello. I am ELIZA. What is your name?"));
        MessageInfo messageHandler = acceptUserResponse();
        String username = identifyUsername(messageHandler.getResponseMessage());
        setupResponses(responses, username);
        sendMessage(new MessageInfo("Hello " + username + ", how are you today?"));
        messageHandler = acceptUserResponse();
        String wellBeingKeyWord = identifyWellBeing(messageHandler.getResponseMessage());
        sendMessage(new MessageInfo(responses.get(wellBeingKeyWord)));
        sendMessage(new MessageInfo("That is it for today. Have a nice day!"));
    }

    private void setupResponses(Map<String,String> responses, String username){
        responses.put("gut","That is great news " + username + ".");
        responses.put("schlecht","I hope you get better " + username + ".");
        responses.put("ok","That is great news " + username + ".");
        responses.put("perfekt","That is great news " + username + ".");
        responses.put("krank,","I hope you get better " + username + ".");
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

    private String identifyWellBeing(String userResponse){
        String [] words = userResponse.replaceAll("[^a-zA-Z0-9]", " ").split(" ");
        for(String key_word : words ){
            if(responses.containsKey(key_word))
                return key_word;
        }
        return null;
    }

}
