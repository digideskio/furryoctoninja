package pl.rspective.mckinsey;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;

import pl.rspective.mckinsey.architecture.dagger.modules.AppModule;
import pl.rspective.mckinsey.dagger.Injector;

public class McKinseyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics.start(this);
        Injector.initDaggerGraph(new Object[]{new AppModule(this)});
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
