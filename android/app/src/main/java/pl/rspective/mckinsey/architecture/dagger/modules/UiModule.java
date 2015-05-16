package pl.rspective.mckinsey.architecture.dagger.modules;


import dagger.Module;
import pl.rspective.mckinsey.ui.LoginActivity;
import pl.rspective.mckinsey.ui.form.MasterFormFragment;
import pl.rspective.mckinsey.ui.results.ResultFragment;
import pl.rspective.mckinsey.ui.users.MasterUserFragment;
import pl.rspective.mckinsey.ui.users.UserListFragment;

@Module(
        injects = {
                LoginActivity.class,
                MasterFormFragment.class,
                MasterUserFragment.class,
                UserListFragment.class,
                ResultFragment.class
        },
        includes = {
                PresenterModule.class
        },
        complete = false,
        library = true
)
public final class UiModule {
}

