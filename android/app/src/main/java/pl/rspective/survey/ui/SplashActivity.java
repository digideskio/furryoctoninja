package pl.rspective.survey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.onesignal.OneSignal;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.survey.R;
import pl.rspective.survey.dagger.Injector;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY_MILLIS = 1500;

    @InjectView(R.id.iv_splash_logo)
    ImageView ivLogo;

    @Inject
    UserRepository userRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        Injector.inject(this);

        initOneSignalPushProvider();

        YoYo.with(Techniques.FadeIn)
                .duration(SPLASH_DELAY_MILLIS)
                .playOn(ivLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (userRepository.loadUser().getToken().isEmpty()) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY_MILLIS);

    }

    @Override
    protected void onPause() {
        super.onPause();
        OneSignal.onPaused();
    }
    @Override
    protected void onResume() {
        super.onResume();
        OneSignal.onResumed();
    }

    private void initOneSignalPushProvider() {
        OneSignal.init(this, "830542138421", "4e20a7ca-fd86-11e4-b8d4-83591acb8ccc");
        OneSignal.sendTag("app", "furryoctoninja");
    }

}
