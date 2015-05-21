package pl.rspective.mckinsey.mvp.views;

import pl.rspective.data.entity.Survey;
import pl.rspective.mckinsey.mvp.BaseView;

public interface IFormResultView extends BaseView {

    void updateUi(Survey survey);

    void showResultFragment();

    void showFormFragment();

}
