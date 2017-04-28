package tr.firefighters.hobilandgelecegiyazanlar.Comment;

import java.util.ArrayList;

/**
 * Created by eniserkaya on 19.04.2017.
 */

public class CommentClass {
    private String userComment;
    private String userId;
    private String userName;
    private ArrayList<String> likes;
    private String photoUrl;
    public CommentClass(){}

    public CommentClass(String userComment, String userId, String userName, ArrayList<String> likes,String photoUrl) {
        this.userComment = userComment;
        this.userId = userId;
        this.userName = userName;
        this.likes = likes;
        this.photoUrl=photoUrl;
    }

    public String getUserComment() {
        return userComment;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
