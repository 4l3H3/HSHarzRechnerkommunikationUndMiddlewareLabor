import java.io.*;
import java.net.Socket;

public class ClientApplication {
    private Socket socket;
    private MessageInfo messageHandler;
    private float challengeSolution;

    public ClientApplication(int port){
        connectToServer(port);
        readServerChallenge();
        readUserInput();
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
            messageHandler = (MessageInfo) iis.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void readUserInput() {
        System.out.println("Todays challenge is: " + messageHandler.getChallengeString());
        BufferedReader iis = new BufferedReader(new InputStreamReader(System.in));
        try {
            challengeSolution = Float.parseFloat(iis.readLine());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendSolvedChallenge(){
        ObjectOutputStream oos;
        try {
            messageHandler.setResponseMessage(challengeSolution);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(messageHandler);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readResponse(){
        BufferedReader is;
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(is.readLine());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
