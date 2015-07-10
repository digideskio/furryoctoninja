package pl.rspective.survey.architecture.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.survey.SurveyApp;


@Module(
        includes = {
                UiModule.class,
                DataModule.class,
                RestModule.class,
                BusModule.class
        },
        injects = {
        },
        library = true
)
public final class AppModule {

    private SurveyApp application;

    public AppModule(SurveyApp app) {
        this.application = app;
    }

    @Provides
    @Singleton
    SurveyApp provideApplication() {
        return application;
    }
}