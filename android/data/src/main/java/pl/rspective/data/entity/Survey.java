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

    @SerializedName("CompletedByUser")
    private boolean submited;

    private boolean ready;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCreatedDate() { // FIXME long
        return createdDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isSubmited() {
        return submited;
    }

    public void setSubmited(boolean submited) {
        this.submited = submited;
    }
}
