package tr.firefighters.hobilandgelecegiyazanlar.User;

/**
 * Created by bthnorhan on 20.04.2017.
 */

public class UserClass {

    public UserClass(){}

    private String userEmail;
    private String userPhoto;
    private String userId;

    public UserClass(String userEmail, String userPhoto, String userId) {
        this.userEmail = userEmail;
        this.userPhoto = userPhoto;
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
