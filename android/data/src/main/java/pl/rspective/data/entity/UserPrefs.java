package pl.rspective.data.entity;

import pl.rspective.data.rest.model.UserRole;

public class UserPrefs {

    public static final UserPrefs USER_DEFAULT = new UserPrefs("", UserRole.UNKNOWN);

    private String token;

    private UserRole role;

    public UserPrefs() {}

    public UserPrefs(String token, UserRole role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
