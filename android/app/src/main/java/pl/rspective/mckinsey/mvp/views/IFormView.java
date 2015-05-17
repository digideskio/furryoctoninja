package pl.rspective.mckinsey.mvp.views;

import pl.rspective.data.entity.Survey;
import pl.rspective.mckinsey.mvp.BaseView;

public interface IFormView extends BaseView {

    void updateUi(Survey survey);

    void showSubmitButton();

    void showResultFragment();

}
