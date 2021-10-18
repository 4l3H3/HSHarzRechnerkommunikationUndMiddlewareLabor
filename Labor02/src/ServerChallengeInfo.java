import java.io.Serializable;

public class ServerChallengeInfo implements Serializable {
    private String challengeString;
    private float challengeSolution;
    public ServerChallengeInfo(String challengeString){
        this.challengeString = challengeString;
    }
    public void setChallengeSolution(float solution){
        challengeSolution = solution;
    }

    public String getChallengeString(){
        return challengeString;
    }

    public float getChallengeSolution(){
        return  challengeSolution;
    }
}
