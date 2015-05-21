package pl.rspective.data.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SurveySubmitRequest {

    @SerializedName("Id")
    private long id;

    @SerializedName("Modified")
    private long timestamp;

    @SerializedName("Answers")
    private List<AnswerRequest> surveyAnswers;

    public SurveySubmitRequest() {
    }

    public SurveySubmitRequest(long id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
        this.surveyAnswers = new ArrayList<>();
    }

    public List<AnswerRequest> getSurveyAnswers() {
        return surveyAnswers;
    }

    public void addAnswer(long questionId, long answerId) {
        surveyAnswers.add(new AnswerRequest(questionId, answerId));
    }

}
