package pl.rspective.mckinsey.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;

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
import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;


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

        loginPresenter.onResume(this);
        btnLogin.setMode(ActionProcessButton.Mode.ENDLESS);

        loginChangeObservable = WidgetObservable.text(etLogin.getEditText());
        passwordChangeObservable = WidgetObservable.text(etPassword.getEditText());

        combineFieldsEvents();

        typeface(this);
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

    @Override
    public void showErrorMessage(@StringRes int messageResId) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .color(Color.parseColor("#f44336"))
                        .type(SnackbarType.SINGLE_LINE)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                        .text(getString(messageResId)));
    }

    @Override
    public void runMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
