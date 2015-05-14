package pl.rspective.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("Id")
    private long id;

    @SerializedName("Text")
    private String text;

    @SerializedName("Answers")
    private List<Answer> answers;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
