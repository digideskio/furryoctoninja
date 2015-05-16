package pl.rspective.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurveyResult {

    @SerializedName("Questions")
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }
}
