package pl.rspective.mckinsey.mvp.presenters;

import pl.rspective.mckinsey.mvp.BasePresenter;
import pl.rspective.mckinsey.mvp.views.ILoginView;

public interface ILoginPresenter extends BasePresenter<ILoginView> {

    void login(String login, String password);

}