package pl.rspective.survey.mvp.presenters;

import pl.rspective.data.entity.Answer;
import pl.rspective.data.entity.Question;
import pl.rspective.survey.mvp.BasePresenter;
import pl.rspective.survey.mvp.views.IFormView;

public interface IFormPresenter extends BasePresenter<IFormView> {

    void updateSurvey(int number, Question question, Answer answer);

    void loadSurvey();

    void submitSurvey();

    void resetSurvey();

}
