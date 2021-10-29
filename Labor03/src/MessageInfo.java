import java.io.Serializable;

public class MessageInfo implements Serializable {
    private String message;
    private String responseMessage;
    public MessageInfo(String message){
        this.message = message;
    }
    public void setResponseMessage(String responseMessage){
        this.responseMessage = responseMessage;
    }

    public String getChallengeString(){
        return message;
    }

    public String getResponseMessage(){
        return  responseMessage;
    }
}
