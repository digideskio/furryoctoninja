package pl.rspective.mckinsey.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.iangclifton.android.floatlabel.FloatLabel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.mvp.presenters.ILoginPresenter;
import pl.rspective.mckinsey.mvp.views.ILoginView;
import rx.Observable;
import rx.Subscription;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func2;

import static com.google.common.base.Strings.isNullOrEmpty;


public class LoginActivity extends AppCompatActivity implements ILoginView {

    @Inject
    ILoginPresenter loginPresenter;

    @InjectView(R.id.btn_login)
    ActionProcessButton btnLogin;

    @InjectView(R.id.etLogin)
    FloatLabel etLogin;

    @InjectView(R.id.etPassword)
    FloatLabel etPassword;

    private Observable<OnTextChangeEvent> loginChangeObservable;
    private Observable<OnTextChangeEvent> passwordChangeObservable;

    private Subscription validationSubscription;

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

        loginPresenter.login(etLogin.getEditText().getText().toString(), etPassword.getEditText().getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(validationSubscription != null) {
            validationSubscription.unsubscribe();
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void enableLoginButton() {
        btnLogin.setEnabled(true);
        btnLogin.setProgress(0);
    }
}
