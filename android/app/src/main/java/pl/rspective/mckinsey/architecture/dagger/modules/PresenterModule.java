package pl.rspective.mckinsey.architecture.dagger.modules;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.repository.LoginRepository;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.mckinsey.data.providers.MenuProvider;
import pl.rspective.mckinsey.mvp.presenters.FormPresenter;
import pl.rspective.mckinsey.mvp.presenters.IFormPresenter;
import pl.rspective.mckinsey.mvp.presenters.ILoginPresenter;
import pl.rspective.mckinsey.mvp.presenters.IMainPresenter;
import pl.rspective.mckinsey.mvp.presenters.IUserPresenter;
import pl.rspective.mckinsey.mvp.presenters.LoginPresenter;
import pl.rspective.mckinsey.mvp.presenters.MainPresenter;
import pl.rspective.mckinsey.mvp.presenters.UserPresenter;

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
    public IMainPresenter provideMainPresenter(MenuProvider menuProvider, UserRepository userRepository, SurveyLocalStorage<String> surveyLocalStorage) {
        return new MainPresenter(menuProvider, userRepository, surveyLocalStorage);
    }

    @Provides
    @Singleton
    public ILoginPresenter provideLoginPresenter(UserRepository userRepositor, LoginRepository loginRepository) {
        return new LoginPresenter(userRepositor, loginRepository);
    }

    @Provides
    @Singleton
    public IFormPresenter provideFormPresenter(Bus bus, SurveyRepository surveyRepository, SurveyLocalStorage<String> localStorage, LocalPreferences localPreferences) {
        return new FormPresenter(bus, surveyRepository, localStorage, localPreferences);
    }

    @Provides
    @Singleton
    public IUserPresenter provideUserPresenter(SurveyRepository surveyRepository) {
        return new UserPresenter(surveyRepository);
    }
}
