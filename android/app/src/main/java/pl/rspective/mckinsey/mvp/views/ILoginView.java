package pl.rspective.mckinsey.mvp.views;

import android.support.annotation.StringRes;

import pl.rspective.mckinsey.mvp.BaseView;

public interface ILoginView extends BaseView {

    void enableLoginButton();

    void showErrorMessage(@StringRes int messageResId);

    void runMainActivity();

}
