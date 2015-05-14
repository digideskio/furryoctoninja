package pl.rspective.mckinsey.architecture.dagger.modules;


import dagger.Module;
import pl.rspective.mckinsey.ui.LoginActivity;
import pl.rspective.mckinsey.ui.form.MasterFormFragment;

@Module(
        injects = {
                LoginActivity.class,
                MasterFormFragment.class
        },
        includes = {
                PresenterModule.class
        },
        complete = false,
        library = true
)
public final class UiModule {
}

