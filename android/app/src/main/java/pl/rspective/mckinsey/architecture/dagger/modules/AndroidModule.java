package pl.rspective.mckinsey.architecture.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.rspective.mckinsey.McKinseyApp;

@Module(
        complete = false,
        library = true
)
public final class AndroidModule {

    @Provides
    @Singleton
    Resources provideResources(McKinseyApp app) {
        return app.getResources();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(McKinseyApp app) {
        return app.getSharedPreferences("local.mckinsey", Context.MODE_PRIVATE);
    }

}