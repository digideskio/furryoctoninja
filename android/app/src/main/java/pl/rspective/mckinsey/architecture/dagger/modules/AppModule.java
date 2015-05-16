package pl.rspective.mckinsey.architecture.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.mckinsey.McKinseyApp;


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

    private McKinseyApp application;

    public AppModule(McKinseyApp app) {
        this.application = app;
    }

    @Provides
    @Singleton
    McKinseyApp provideApplication() {
        return application;
    }
}