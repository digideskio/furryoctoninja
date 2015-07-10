package pl.rspective.survey.architecture.dagger.modules;


import dagger.Module;
import pl.rspective.survey.ui.LoginActivity;
import pl.rspective.survey.ui.MainActivity;
import pl.rspective.survey.ui.SplashActivity;
import pl.rspective.survey.ui.form.FormQuestionFragment;
import pl.rspective.survey.ui.form.MasterFormFragment;
import pl.rspective.survey.ui.results.ResultFragment;
import pl.rspective.survey.ui.users.MasterUserFragment;
import pl.rspective.survey.ui.users.UserListFragment;

@Module(
        injects = {
                SplashActivity.class,
                MainActivity.class,
                LoginActivity.class,
                FormQuestionFragment.class,
                MasterFormFragment.class,
                MasterUserFragment.class,
                UserListFragment.class,
                ResultFragment.class
        },
        includes = {
                PresenterModule.class
        },
        complete = false,
        library = true
)
public final class UiModule {
}

