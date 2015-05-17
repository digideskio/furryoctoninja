package pl.rspective.data.rest.model;

import com.google.gson.annotations.SerializedName;

public enum UserRole {

    @SerializedName("Admin")
    ADMIN,

    @SerializedName("User")
    USER;
}
