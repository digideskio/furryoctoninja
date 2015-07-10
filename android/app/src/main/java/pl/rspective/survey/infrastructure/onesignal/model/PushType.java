package pl.rspective.survey.infrastructure.onesignal.model;

import com.google.gson.annotations.SerializedName;

public enum PushType {

    @SerializedName("SURVEY-CHANGED")
    SURVEY_CHANGED,

    @SerializedName("SURVEY-RESULTS-UPDATED")
    SURVEY_RESULTS_UPDATED,

    @SerializedName("SURVEY-RESTART")
    SURVEY_RESTART;
}
