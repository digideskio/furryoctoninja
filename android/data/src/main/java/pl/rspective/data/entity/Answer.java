package pl.rspective.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable {

    @SerializedName("Id")
    private long id;

    @SerializedName("Text")
    private String text;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
