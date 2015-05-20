package pl.rspective.mckinsey.mvp.presenters;

import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yalantis.contextmenu.lib.MenuObject;

import java.util.List;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.local.model.StorageType;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.data.rest.model.UserRole;
import pl.rspective.mckinsey.data.model.AppEventStatus;
import pl.rspective.mckinsey.data.providers.MenuProvider;
import pl.rspective.mckinsey.mvp.views.IMainView;
import pl.rspective.mckinsey.ui.form.MasterFormFragment;
import pl.rspective.mckinsey.ui.results.ResultFragment;

public class MainPresenter implements IMainPresenter {

    private LocalPreferences localPreferences;
    private MenuProvider menuProvider;
    private UserRepository userRepository;
    private SurveyLocalStorage<String> surveyLocalStorage;

    private IMainView view;

    @Inject
    public MainPresenter(LocalPreferences localPreferences, MenuProvider menuProvider, UserRepository userRepository, SurveyLocalStorage<String> surveyLocalStorage) {
        this.localPreferences = localPreferences;
        this.menuProvider = menuProvider;
        this.userRepository = userRepository;
        this.surveyLocalStorage =  surveyLocalStorage;
    }

    @Override
    public Fragment getStartFragment() {
        if(userRepository.loadUser().getRole().equals(UserRole.ADMIN)) {
            return ResultFragment.newInstance();
        }

        Gson gson = new GsonBuilder().create();
        Survey survey = gson.fromJson(surveyLocalStorage.load(StorageType.SURVEY), Survey.class);

        if(survey == null || !survey.isSubmited()) {
            return MasterFormFragment.newInstance();
        } else {
            return ResultFragment.newInstance();
        }
    }

    @Override
    public List<MenuObject> getMainMenuItems() {
        return menuProvider.provideMenuList(userRepository.loadUser().getRole());
    }

    @Override
    public void checkAppEventStatus() {
        AppEventStatus eventStatus = AppEventStatus.valueOf(localPreferences.getAppEventStatus());

        switch (eventStatus) { //TODO Clear when we need, etc
            case SURVEY_CHANGED_PUSH_MESSAGE:
                break;
            case SURVEY_RESTART_PUSH_MESSAGE:
                localPreferences.setSurveyLoaded(true);
                view.showSurveyReloadDialog();
                break;
            case NO_EVENTS:
                break;
        }

        localPreferences.setAppEventStatus(AppEventStatus.NO_EVENTS.ordinal());
    }

    @Override
    public void userLogout() {
        userRepository.clearUser();
    }

    @Override
    public void onResume(IMainView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
