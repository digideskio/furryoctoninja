package pl.rspective.mckinsey.mvp.presenters;

import pl.rspective.data.entity.Survey;
import pl.rspective.mckinsey.mvp.BasePresenter;
import pl.rspective.mckinsey.mvp.views.IFormView;

public interface IFormPresenter extends BasePresenter<IFormView> {

    void updateSurvey(Survey survey);

    void loadSurvey();

}
