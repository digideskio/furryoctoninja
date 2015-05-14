package pl.rspective.mckinsey.architecture.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.repository.LoginRepository;
import pl.rspective.mckinsey.mvp.presenters.ILoginPresenter;
import pl.rspective.mckinsey.mvp.presenters.LoginPresenter;

@Module(
        injects = {
                LoginPresenter.class
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
}
