import java.io.Serializable;

public class ServerChallengeInfo implements Serializable {
    private String challengeString;
    public ServerChallengeInfo(String challengeString){
        this.challengeString = challengeString;
    }
}
