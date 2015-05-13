package pl.rspective.mckinsey.architecture.dagger.modules;


import dagger.Module;
import pl.rspective.mckinsey.ui.LoginActivity;

@Module(
        injects = {
                LoginActivity.class
        },
        includes = {
                PresenterModule.class
        },
        complete = false,
        library = true
)
public final class UiModule {
}

