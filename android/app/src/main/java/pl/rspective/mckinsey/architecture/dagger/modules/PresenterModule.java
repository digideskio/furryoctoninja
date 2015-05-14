package pl.rspective.mckinsey.architecture.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.repository.LoginRepository;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.mckinsey.mvp.presenters.FormPresenter;
import pl.rspective.mckinsey.mvp.presenters.IFormPresenter;
import pl.rspective.mckinsey.mvp.presenters.ILoginPresenter;
import pl.rspective.mckinsey.mvp.presenters.LoginPresenter;

@Module(
        injects = {
                LoginPresenter.class,
                FormPresenter.class
        },
        includes = {
        },
        complete = false,
        library = true
)
public class PresenterModule {

    @Provides
    @Singleton
    public ILoginPresenter provideLoginPresenter(LoginRepository loginRepository) {
        return new LoginPresenter(loginRepository);
    }

    @Provides
    @Singleton
    public IFormPresenter provideFormPresenter(SurveyRepository surveyRepository) {
        return new FormPresenter(surveyRepository);
    }
}
