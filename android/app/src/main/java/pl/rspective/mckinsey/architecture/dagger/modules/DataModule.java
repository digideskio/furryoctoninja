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

@Module(
        injects = {
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
    UserRepository provideUserRepository() {
        return new McKinseyUserRepository();
    }

}
