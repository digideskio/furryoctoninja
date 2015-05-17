package pl.rspective.data.rest.model;

import com.google.gson.annotations.SerializedName;

public class AnswerRequest {
        @SerializedName("QuestionId")
        private long questionId;

        @SerializedName("AnswerId")
        private long answerId;

        public AnswerRequest() {
        }

        public AnswerRequest(long questionId, long answerId) {
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