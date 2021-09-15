package  com.farias.fariastpintegrador.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private int avatar;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {

        this.displayName = displayName;
        this.avatar = avatar;
    }

    String getDisplayName() {
        return displayName;
    }

    int getAvatar() { return avatar; }
}