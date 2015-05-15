package pl.rspective.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Survey {

    @SerializedName("Id")
    private long id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Description")
    private String description;

    @SerializedName("CreatedDate")
    private long createdDate;

    @SerializedName("Questions")
    private List<Question> questions;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
