package pl.rspective.mckinsey.mvp.views;

import pl.rspective.mckinsey.mvp.BaseView;

public interface ILoginView extends BaseView {

    void enableLoginButton();

    void showErrorMessage();

    void runMainActivity();

}
