package pl.rspective.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable {

    @SerializedName("Id")
    private long id;

    @SerializedName("Text")
    private String text;

    @SerializedName("Count")
    private int count;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getCount() {
        return count;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
