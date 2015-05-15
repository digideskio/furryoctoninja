package pl.rspective.data.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
{ id:”id ankiety”,timestamp:”12345623432”,questions: [
    { pytanie: id, odpowiedz: id },
        …
        ]
        }
 */
public class SurveySubmitRequest {

    @SerializedName("Id")
    private long id;

    private long timestamp;

    @SerializedName("questions")
    private List<AnswerRequest> surveyAnswers;

    public SurveySubmitRequest(long id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public List<AnswerRequest> getSurveyAnswers() {
        return surveyAnswers;
    }

    public void setSurveyAnswers(List<AnswerRequest> surveyAnswers) {
        this.surveyAnswers = surveyAnswers;
    }

    private class AnswerRequest {
        private long questionId;
        private long answerId;

        private AnswerRequest(long questionId, long answerId) {
            this.questionId = questionId;
            this.answerId = answerId;
        }

        public long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }

        public long getAnswerId() {
            return answerId;
        }

        public void setAnswerId(long answerId) {
            this.answerId = answerId;
        }
    }

}
