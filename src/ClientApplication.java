import java.io.*;
import java.net.Socket;

public class ClientApplication {
    private Socket socket;
    private ServerChallengeInfo serverChallengeInfo;
    private int challengeSolution;

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
            serverChallengeInfo = (ServerChallengeInfo) iis.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void readUserInput() {
        System.out.println("Todays challenge is: " + serverChallengeInfo.getChallengeSolution());
        BufferedReader iis = new BufferedReader(new InputStreamReader(System.in));
        try {
            challengeSolution = Integer.parseInt(iis.readLine());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendSolvedChallenge(){
        ObjectOutputStream oos;
        try {
            serverChallengeInfo.setChallengeSolution(challengeSolution);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(serverChallengeInfo);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }




}
