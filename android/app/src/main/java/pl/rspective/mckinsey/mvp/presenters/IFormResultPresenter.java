package pl.rspective.mckinsey.mvp.presenters;

import pl.rspective.mckinsey.mvp.BasePresenter;
import pl.rspective.mckinsey.mvp.views.IFormResultView;

public interface IFormResultPresenter extends BasePresenter<IFormResultView> {

    void loadSurveyResults();

    void restartSurvey();

}
