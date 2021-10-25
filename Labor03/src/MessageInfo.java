import java.io.Serializable;

public class MessageInfo implements Serializable {
    private String challengeString;
    private float challengeSolution;
    public MessageInfo(String challengeString){
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
