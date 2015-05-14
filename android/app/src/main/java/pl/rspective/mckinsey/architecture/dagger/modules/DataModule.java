package pl.rspective.mckinsey.architecture.dagger.modules;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.data.local.McKinseySurveyStorage;
import pl.rspective.data.local.SurveyStorage;

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
    SurveyStorage<String> provideSurveyStorage(SharedPreferences preferences) {
        return new McKinseySurveyStorage(preferences);
    }

}
