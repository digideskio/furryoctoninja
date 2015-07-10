package pl.rspective.survey.architecture.dagger.modules;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalPreferences;
import pl.rspective.data.local.SurveyStorageSurvey;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.repository.SurveyUserRepository;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.survey.data.providers.SurveyMenuProvider;
import pl.rspective.survey.data.providers.MenuProvider;
import pl.rspective.survey.infrastructure.onesignal.OneSignalReceiver;

@Module(
        injects = {
                OneSignalReceiver.class,
                SurveyMenuProvider.class
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
        return new SurveyStorageSurvey(preferences);
    }

    @Provides
    @Singleton
    LocalPreferences provideLocalPreferences(SharedPreferences preferences) {
        return new SurveyLocalPreferences(preferences);
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(SharedPreferences preferences) {
        return new SurveyUserRepository(preferences);
    }

    @Provides
    @Singleton
    MenuProvider provideMenuProvider() {
        return new SurveyMenuProvider();
    }

}
