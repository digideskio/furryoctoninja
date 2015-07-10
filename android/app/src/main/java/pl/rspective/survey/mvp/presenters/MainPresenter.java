package pl.rspective.survey.mvp.presenters;

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
import pl.rspective.survey.data.model.AppEventStatus;
import pl.rspective.survey.data.providers.MenuProvider;
import pl.rspective.survey.mvp.views.IMainView;
import pl.rspective.survey.ui.form.MasterFormFragment;
import pl.rspective.survey.ui.results.ResultFragment;

public class MainPresenter implements IMainPresenter {

    private LocalPreferences localPreferences;
    private MenuProvider menuProvider;
    private UserRepository userRepository;
    private SurveyLocalStorage<String> surveyLocalStorage;

    private IMainView view;

    private Gson gson;

    @Inject
    public MainPresenter(LocalPreferences localPreferences, MenuProvider menuProvider, UserRepository userRepository, SurveyLocalStorage<String> surveyLocalStorage) {
        this.localPreferences = localPreferences;
        this.menuProvider = menuProvider;
        this.userRepository = userRepository;
        this.surveyLocalStorage =  surveyLocalStorage;

        this.gson = new GsonBuilder().create();
    }

    @Override
    public Fragment getStartFragment() {
        if(isAdmin()) {
            return ResultFragment.newInstance();
        }

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

        switch (eventStatus) {
            case SURVEY_CHANGED_PUSH_MESSAGE:
                Survey survey = gson.fromJson(surveyLocalStorage.load(StorageType.SURVEY), Survey.class);

                if(survey == null || !survey.isSubmited() && userRepository.loadUser().getRole().equals(UserRole.USER)) {
                    localPreferences.setSurveyLoaded(true);
                    surveyLocalStorage.clear(StorageType.SURVEY);
                    view.showSurveyReloadDialog();
                }
                break;
            case SURVEY_RESTART_PUSH_MESSAGE:
                localPreferences.setSurveyLoaded(true);
                surveyLocalStorage.clear(StorageType.SURVEY);
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
    public boolean isAdmin() {
        return userRepository.loadUser().getRole().equals(UserRole.ADMIN);
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
