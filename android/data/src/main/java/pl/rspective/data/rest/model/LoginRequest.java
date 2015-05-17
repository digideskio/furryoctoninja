package pl.rspective.data.rest.model;

public class LoginRequest {

    public static final String CLIENT_ID = "p35iw0R6RO1730BSK432qswrZldwY0jR";

    private String login;
    private String password;
    private String clientId;

    public LoginRequest(String login, String password) {
        this.clientId = CLIENT_ID;
        this.login = login;
        this.password = password;
    }
}
