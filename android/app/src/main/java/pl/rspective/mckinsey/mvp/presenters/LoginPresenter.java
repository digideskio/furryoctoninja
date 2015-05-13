package pl.rspective.mckinsey.mvp.presenters;

import javax.inject.Inject;

import pl.rspective.data.repository.LoginRepository;
import pl.rspective.mckinsey.mvp.views.ILoginView;
import retrofit.client.Response;
import rx.Subscription;
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
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.enableLoginButton();
                    }
                });
    }
}
