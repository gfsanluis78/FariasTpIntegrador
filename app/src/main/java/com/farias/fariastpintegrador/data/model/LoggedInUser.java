package com.farias.fariastpintegrador.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private int avatar;

    public LoggedInUser(String userId, String displayName, int avatar) {
        this.userId = userId;
        this.displayName = displayName;
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAvatar() { return avatar; }
}