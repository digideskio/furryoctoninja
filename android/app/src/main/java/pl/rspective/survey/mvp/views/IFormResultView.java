package pl.rspective.survey.mvp.views;

import pl.rspective.data.entity.Survey;
import pl.rspective.survey.mvp.BaseView;

public interface IFormResultView extends BaseView {

    void updateUi(Survey survey);

    void showResultFragment();

    void showFormFragment();

}
