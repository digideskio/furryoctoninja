package pl.rspective.survey.mvp.presenters;

import javax.inject.Inject;

import pl.rspective.data.entity.UserPrefs;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.local.model.StorageType;
import pl.rspective.data.repository.LoginRepository;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.data.rest.model.LoginResponse;
import pl.rspective.survey.R;
import pl.rspective.survey.mvp.views.ILoginView;
import retrofit.RetrofitError;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class LoginPresenter implements ILoginPresenter {

    private UserRepository userRepository;
    private LoginRepository loginRepository;
    private SurveyLocalStorage<String> surveyLocalStorage;
    private LocalPreferences localPreferences;

    private Subscription loginSubscription;
    private ILoginView view;

    @Inject
    public LoginPresenter(UserRepository userRepository, LoginRepository loginRepository,
                          SurveyLocalStorage<String> surveyLocalStorage, LocalPreferences localPreferences) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
        this.surveyLocalStorage = surveyLocalStorage;
        this.localPreferences = localPreferences;
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
                        userRepository.saveUser(new UserPrefs(response.getToken(), response.getUserRoles().get(0)));
                        surveyLocalStorage.clear(StorageType.SURVEY);
                        localPreferences.setSurveyLoaded(true);
                        view.runMainActivity();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.enableLoginButton();
                        view.showErrorMessage(translateError(throwable));
                    }
                });
    }

    private int translateError(Throwable throwable) {
        if (throwable instanceof RetrofitError) {
            RetrofitError retrofitError = (RetrofitError) throwable;
            if (retrofitError.getKind() == RetrofitError.Kind.NETWORK) {
                return R.string.network_error;
            } else if (retrofitError.getResponse().getStatus() == 403) {
                return R.string.invalid_login_or_password;
            } else if (retrofitError.getResponse().getStatus() == 500) {
                return R.string.internal_server_error;
            } else {
                return R.string.unkown_login_error;
            }
        } else {
            return R.string.unkown_login_error;
        }
    }
}
