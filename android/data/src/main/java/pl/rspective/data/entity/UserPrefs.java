package pl.rspective.data.entity;

public class UserPrefs {

    public static final UserPrefs USER_DEFAULT = new UserPrefs("", "");

    private String token;

    private String role;

    public UserPrefs() {}

    public UserPrefs(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
