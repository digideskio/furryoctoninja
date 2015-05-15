package pl.rspective.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class McKinseyLocalPreferences implements LocalPreferences {

    private static final String FIRST_USER_SUCCESS_LOGIN_KEY = "mckinsey_user_first_success_login";

    private SharedPreferences preferences;

    @Inject
    public McKinseyLocalPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void setFirstUserLogin() {
        preferences.edit().putBoolean(FIRST_USER_SUCCESS_LOGIN_KEY, false).commit();
    }

    @Override
    public boolean isUserFirstLogin() {
        return preferences.getBoolean(FIRST_USER_SUCCESS_LOGIN_KEY, true);
    }

}
