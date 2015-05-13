package pl.rspective.mckinsey.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.iangclifton.android.floatlabel.FloatLabel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.rspective.data.repository.LoginRepository;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.dagger.Injector;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func2;

import static com.google.common.base.Strings.isNullOrEmpty;


public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginRepository loginRepository;

    @InjectView(R.id.btn_login)
    ActionProcessButton btnLogin;

    @InjectView(R.id.etLogin)
    FloatLabel etLogin;

    @InjectView(R.id.etPassword)
    FloatLabel etPassword;

    private Observable<OnTextChangeEvent> loginChangeObservable;
    private Observable<OnTextChangeEvent> passwordChangeObservable;

    private Subscription validationSubscription;
    private Subscription loginSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        btnLogin.setMode(ActionProcessButton.Mode.ENDLESS);

        loginChangeObservable = WidgetObservable.text(etLogin.getEditText());
        passwordChangeObservable = WidgetObservable.text(etPassword.getEditText());

        combineFieldsEvents();
    }

    private void combineFieldsEvents() {
        validationSubscription = Observable.combineLatest(loginChangeObservable, passwordChangeObservable, new Func2<OnTextChangeEvent, OnTextChangeEvent, Boolean>() {
            @Override
            public Boolean call(OnTextChangeEvent onLoginChangeEvent, OnTextChangeEvent onPasswordChangeEvent) {
                return !isNullOrEmpty(onLoginChangeEvent.text().toString()) && !isNullOrEmpty(onPasswordChangeEvent.text().toString());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean fieldsValid) {
                btnLogin.setEnabled(fieldsValid);
            }
        });

    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        btnLogin.setEnabled(false);
        btnLogin.setProgress(1);

        loginSubscription = loginRepository.userLogin(etLogin.getEditText().getText().toString(), etPassword.getEditText().getText().toString())
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        btnLogin.setEnabled(true);
                        btnLogin.setProgress(0);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(loginSubscription != null) {
            loginSubscription.unsubscribe();
        }

        if(validationSubscription != null) {
            validationSubscription.unsubscribe();
        }
    }
}
