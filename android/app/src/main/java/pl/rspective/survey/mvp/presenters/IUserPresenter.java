package pl.rspective.survey.mvp.presenters;

import pl.rspective.survey.mvp.BasePresenter;
import pl.rspective.survey.mvp.views.IUserView;

public interface IUserPresenter extends BasePresenter<IUserView> {

    void refreshUserList();

}
