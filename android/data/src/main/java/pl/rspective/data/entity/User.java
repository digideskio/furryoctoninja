package pl.rspective.data.entity;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("UserId")
    private long id;

    @SerializedName("UserName")
    private String name;

    @SerializedName("Completed")
    private boolean completed;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }
}
