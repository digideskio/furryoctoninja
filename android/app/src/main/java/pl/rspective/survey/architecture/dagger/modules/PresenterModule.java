package pl.rspective.survey.architecture.dagger.modules;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.repository.LoginRepository;
import pl.rspective.data.repository.ISurveyRepository;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.survey.data.providers.MenuProvider;
import pl.rspective.survey.mvp.presenters.FormPresenter;
import pl.rspective.survey.mvp.presenters.FormResultPresenter;
import pl.rspective.survey.mvp.presenters.IFormPresenter;
import pl.rspective.survey.mvp.presenters.IFormResultPresenter;
import pl.rspective.survey.mvp.presenters.ILoginPresenter;
import pl.rspective.survey.mvp.presenters.IMainPresenter;
import pl.rspective.survey.mvp.presenters.IUserPresenter;
import pl.rspective.survey.mvp.presenters.LoginPresenter;
import pl.rspective.survey.mvp.presenters.MainPresenter;
import pl.rspective.survey.mvp.presenters.UserPresenter;

@Module(
        injects = {
                LoginPresenter.class,
                FormPresenter.class,
                UserPresenter.class,
                MainPresenter.class
        },
        includes = {
        },
        complete = false,
        library = true
)
public class PresenterModule {

    @Provides
    @Singleton
    public IMainPresenter provideMainPresenter(LocalPreferences localPreferences, MenuProvider menuProvider, UserRepository userRepository, SurveyLocalStorage<String> surveyLocalStorage) {
        return new MainPresenter(localPreferences, menuProvider, userRepository, surveyLocalStorage);
    }

    @Provides
    @Singleton
    public ILoginPresenter provideLoginPresenter(UserRepository userRepository, LoginRepository loginRepository, SurveyLocalStorage<String> localStorage, LocalPreferences localPreferences) {
        return new LoginPresenter(userRepository, loginRepository, localStorage, localPreferences);
    }

    @Provides
    @Singleton
    public IFormPresenter provideFormPresenter(Bus bus, ISurveyRepository ISurveyRepository, UserRepository userRepository, SurveyLocalStorage<String> localStorage, LocalPreferences localPreferences) {
        return new FormPresenter(bus, ISurveyRepository, userRepository, localStorage, localPreferences);
    }

    @Provides
    @Singleton
    public IUserPresenter provideUserPresenter(ISurveyRepository ISurveyRepository) {
        return new UserPresenter(ISurveyRepository);
    }

    @Provides
    @Singleton
    public IFormResultPresenter provideFormResultPresenter(IFormPresenter formPresenter, ISurveyRepository ISurveyRepository) {
        return new FormResultPresenter(formPresenter, ISurveyRepository);
    }
}
