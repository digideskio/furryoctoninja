package pl.rspective.survey.data.model;

import android.support.annotation.StringRes;

import pl.rspective.survey.R;

public enum SurveySubmitResultType {

    SURVEY_OK(R.string.survey_submit_ok, R.string.survey_submit_btn_close),
    SURVEY_ALREADY_SEND(R.string.survey_submit_already_sent, R.string.survey_submit_btn_close),
    SURVEY_WAS_CHANGED(R.string.survey_submit_was_changed, R.string.survey_submit_btn_close),
    SURVEY_ERROR(R.string.survey_submit_error, R.string.survey_submit_btn_close);

    private  int labelTxtId;
    private  int buttonTxtId;

    SurveySubmitResultType(@StringRes int labelId, @StringRes int buttonTxtId) {
        this.labelTxtId = labelId;
        this.buttonTxtId = buttonTxtId;
    }

    public int getLabelTxtId() {
        return labelTxtId;
    }

    public int getButtonTxtId() {
        return buttonTxtId;
    }
}
