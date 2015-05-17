package pl.rspective.mckinsey.mvp.presenters;

import javax.inject.Inject;

import pl.rspective.data.repository.LoginRepository;
import pl.rspective.data.rest.model.LoginResponse;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.mvp.views.ILoginView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class LoginPresenter implements ILoginPresenter {

    private LoginRepository loginRepository;

    private Subscription loginSubscription;
    private ILoginView view;

    @Inject
    public LoginPresenter(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void onResume(ILoginView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        view = null;

        if(loginSubscription != null) {
            loginSubscription.unsubscribe();
        }
    }

    @Override
    public void login(String login, String password) {
        loginSubscription = loginRepository.userLogin(login, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoginResponse>() {
                    @Override
                    public void call(LoginResponse response) {
                        view.runMainActivity();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.enableLoginButton();
                        view.showErrorMessage(R.string.error_no_internet_connection);
                    }
                });
    }
}
