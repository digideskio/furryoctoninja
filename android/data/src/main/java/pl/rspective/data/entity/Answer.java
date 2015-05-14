package pl.rspective.data.entity;

import com.google.gson.annotations.SerializedName;

public class Answer {

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
