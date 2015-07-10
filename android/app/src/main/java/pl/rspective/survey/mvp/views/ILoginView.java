package pl.rspective.survey.mvp.views;

import android.support.annotation.StringRes;

import pl.rspective.survey.mvp.BaseView;

public interface ILoginView extends BaseView {

    void enableLoginButton();

    void showErrorMessage(@StringRes int messageResId);

    void runMainActivity();

}
