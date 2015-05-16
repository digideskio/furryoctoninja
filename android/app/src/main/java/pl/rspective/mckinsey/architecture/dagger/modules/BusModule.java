package pl.rspective.mckinsey.architecture.dagger.modules;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
        },
        injects = {
        },
        library = true
)
public final class BusModule {

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus();
    }

}
