package tr.firefighters.hobilandgelecegiyazanlar.Message;

/**
 * Created by bthnorhan on 20.04.2017.
 */

public class MessageClass {

    private String message;
    private String user;

    public MessageClass(String message, String user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
