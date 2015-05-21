package pl.rspective.mckinsey;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import pl.rspective.mckinsey.architecture.dagger.modules.AppModule;
import pl.rspective.mckinsey.dagger.Injector;

public class McKinseyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics.start(this);
        Injector.initDaggerGraph(new Object[]{new AppModule(this)});

        initCustomTypeface();
    }

    private void initCustomTypeface() {
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Roboto-Italic.ttf"))
                .create();

        TypefaceHelper.init(typeface);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
