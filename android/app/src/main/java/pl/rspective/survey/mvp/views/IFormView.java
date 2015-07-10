package pl.rspective.survey.mvp.views;

import pl.rspective.data.entity.Survey;
import pl.rspective.survey.data.model.SurveySubmitResultType;
import pl.rspective.survey.mvp.BaseView;

public interface IFormView extends BaseView {

    void updateUi(Survey survey);

    void showSubmitButton();

    void showResultFragment();

    void showSubmitDialog(SurveySubmitResultType type);

    void nextQuestion(int position);

}
