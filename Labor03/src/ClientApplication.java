import java.io.*;
import java.net.Socket;

public class ClientApplication {
    private Socket socket;
    private MessageInfo messageHandler;
    private String userResponse;

    public ClientApplication(int port){
        connectToServer(port);
        readServerMessage();
        readUserInput();
        sendResponse();
        readServerMessage();
        readUserInput();
        sendResponse();
        readServerMessage();
        readServerMessage();
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

    private void readServerMessage(){
        ObjectInputStream iis;
        try {
            iis = new ObjectInputStream(socket.getInputStream());
            messageHandler = (MessageInfo) iis.readObject();
            System.out.println(messageHandler.getMessage());
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void readUserInput() {
        BufferedReader iis = new BufferedReader(new InputStreamReader(System.in));
        try {
            userResponse = iis.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendResponse(){
        ObjectOutputStream oos;
        try {
            messageHandler.setResponseMessage(userResponse);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(messageHandler);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
