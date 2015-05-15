package pl.rspective.mckinsey.mvp.presenters;

import pl.rspective.data.entity.Question;
import pl.rspective.mckinsey.mvp.BasePresenter;
import pl.rspective.mckinsey.mvp.views.IFormView;

public interface IFormPresenter extends BasePresenter<IFormView> {

    void updateSurvey(int number, Question question);

    void loadSurvey();

}
