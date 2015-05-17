package pl.rspective.data.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

    @SerializedName("Token")
    private String token;

    @SerializedName("Expiration")
    private String expiration;

    @SerializedName("Roles")
    private List<UserRole> userRoles;

    public String getToken() {
        return token;
    }

    public String getExpiration() {
        return expiration;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }
}
