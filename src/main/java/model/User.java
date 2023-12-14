package model;

public class User {
    int userId;
    String userGuid;
    String userName;

    public User(int userId, String userGuid, String userName) {
        this.userId = userId;
        this.userGuid = userGuid;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public String getUserName() {
        return userName;
    }
}