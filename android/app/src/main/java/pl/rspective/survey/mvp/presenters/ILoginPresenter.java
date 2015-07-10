package pl.rspective.survey.mvp.presenters;

import pl.rspective.survey.mvp.BasePresenter;
import pl.rspective.survey.mvp.views.ILoginView;

public interface ILoginPresenter extends BasePresenter<ILoginView> {

    void login(String login, String password);

}