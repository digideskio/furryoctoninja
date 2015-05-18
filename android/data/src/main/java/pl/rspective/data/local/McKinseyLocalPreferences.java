package pl.rspective.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class McKinseyLocalPreferences implements LocalPreferences {

    private static final String FIRST_USER_SUCCESS_LOGIN_KEY = "mckinsey_user_first_success_login";
    private static final String APP_EVENT_STATUS_KEY = "mckinsey_app_event_status";

    private SharedPreferences preferences;

    @Inject
    public McKinseyLocalPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void setFirstUserLogin(boolean firstLogin) {
        preferences.edit().putBoolean(FIRST_USER_SUCCESS_LOGIN_KEY, firstLogin).commit();
    }

    @Override
    public boolean isUserFirstLogin() {
        return preferences.getBoolean(FIRST_USER_SUCCESS_LOGIN_KEY, true);
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
