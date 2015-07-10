package pl.rspective.survey.architecture.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.survey.SurveyApp;

@Module(
        complete = false,
        library = true
)
public final class AndroidModule {

    @Provides
    @Singleton
    Resources provideResources(SurveyApp app) {
        return app.getResources();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(SurveyApp app) {
        return app.getSharedPreferences("local.mckinsey", Context.MODE_PRIVATE);
    }

}