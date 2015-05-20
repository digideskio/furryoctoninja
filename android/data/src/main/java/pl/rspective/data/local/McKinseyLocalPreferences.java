package pl.rspective.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class McKinseyLocalPreferences implements LocalPreferences {

    private static final String SURVEY_LOADED_KEY = "mckinsey_survey_loaded";
    private static final String APP_EVENT_STATUS_KEY = "mckinsey_app_event_status";

    private SharedPreferences preferences;

    @Inject
    public McKinseyLocalPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void setSurveyLoaded(boolean firstLogin) {
        preferences.edit().putBoolean(SURVEY_LOADED_KEY, firstLogin).commit();
    }

    @Override
    public boolean isSurveyLoaded() {
        return preferences.getBoolean(SURVEY_LOADED_KEY, true);
    }

    @Override
    public void setAppEventStatus(int status) {
        preferences.edit().putInt(APP_EVENT_STATUS_KEY, status).commit();
    }

    @Override
    public int getAppEventStatus() {
        return preferences.getInt(APP_EVENT_STATUS_KEY, 0);
    }

}
