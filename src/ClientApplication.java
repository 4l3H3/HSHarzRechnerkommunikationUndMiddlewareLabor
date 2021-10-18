public class ClientApplication {

    public ClientApplication(int port){
        connectToServer(port);
        awaitChallenge();
        solveChallenge();
        sendSolvedChallenge();
        readResponse();
    }
    public static void main(String[] args){
        new ClientApplication(8444);
    }

    
}
