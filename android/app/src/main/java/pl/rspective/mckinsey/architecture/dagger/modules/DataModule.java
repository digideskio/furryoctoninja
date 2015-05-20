package pl.rspective.mckinsey.architecture.dagger.modules;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.McKinseyLocalPreferences;
import pl.rspective.data.local.McKinseyStorageSurvey;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.repository.McKinseyUserRepository;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.mckinsey.data.providers.McKinseyMenuProvider;
import pl.rspective.mckinsey.data.providers.MenuProvider;
import pl.rspective.mckinsey.infrastructure.onesignal.OneSignalReceiver;

@Module(
        injects = {
                OneSignalReceiver.class
        },
        includes = {
                AndroidModule.class
        },
        complete = false,
        library = true
)
public final class DataModule {

    @Provides
    @Singleton
    SurveyLocalStorage<String> provideSurveyStorage(SharedPreferences preferences) {
        return new McKinseyStorageSurvey(preferences);
    }

    @Provides
    @Singleton
    LocalPreferences provideLocalPreferences(SharedPreferences preferences) {
        return new McKinseyLocalPreferences(preferences);
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(SharedPreferences preferences) {
        return new McKinseyUserRepository(preferences);
    }

    @Provides
    @Singleton
    MenuProvider provideMenuProvider() {
        return new McKinseyMenuProvider();
    }

}
