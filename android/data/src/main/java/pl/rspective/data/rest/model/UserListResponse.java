package pl.rspective.data.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.rspective.data.entity.User;

public class UserListResponse {

    @SerializedName("Items")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}