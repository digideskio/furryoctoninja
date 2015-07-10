package pl.rspective.survey.mvp.presenters;

import pl.rspective.survey.mvp.BasePresenter;
import pl.rspective.survey.mvp.views.IFormResultView;

public interface IFormResultPresenter extends BasePresenter<IFormResultView> {

    void loadSurveyResults();

    void restartSurvey();

}
