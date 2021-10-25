package com.farias.fariastpintegrador.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String email;
    private int avatar;

    public LoggedInUser(String userId, String displayName, String email, int avatar) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAvatar() { return avatar; }

    public String getEmail() {
        return email;
    }
}