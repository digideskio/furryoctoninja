package pl.rspective.mckinsey.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hkm.ui.processbutton.iml.ActionProcessButton;

import javax.inject.Inject;

import pl.rspective.data.rest.NetworkAction;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.dagger.Injector;
import rx.Observer;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.subjects.PublishSubject;


public class LoginActivity extends AppCompatActivity implements Observer<NetworkAction>  {

    @Inject
    PublishSubject<NetworkAction> networkActivity;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        setContentView(R.layout.activity_login);

        final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignIn.setEnabled(false);
                btnSignIn.setProgress(100);
            }
        });

        subscription = AndroidObservable.bindActivity(this, networkActivity).subscribe(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }


    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(NetworkAction networkAction) {
        switch (networkAction) {
            case HTTP_REQUEST_START:
                break;

            case HTTP_REQUEST_END:
                break;
        }
    }
}
