package pl.rspective.data.repository;

import android.content.SharedPreferences;

import javax.inject.Inject;

import pl.rspective.data.entity.User;
import pl.rspective.data.entity.UserPrefs;
import pl.rspective.data.rest.model.LoginRequest;
import pl.rspective.data.rest.model.UserRole;

public class McKinseyUserRepository implements UserRepository {

    private SharedPreferences preferences;

    private static final String USER_TOKEN_KEY = "mckinsey_user_token";
    private static final String USER_ROLE_KEY = "mckinsey_user_role";

    @Inject
    public McKinseyUserRepository(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void saveUser(UserPrefs userPrefs) {
        if (userPrefs != null) {
            preferences.edit()
                    .putString(USER_TOKEN_KEY, userPrefs.getToken())
                    .putString(USER_ROLE_KEY, userPrefs.getRole().name())
                    .commit();
        } else {
            preferences.edit()
                    .remove(USER_TOKEN_KEY)
                    .remove(USER_ROLE_KEY)
                    .commit();
        }

    }

    @Override
    public UserPrefs loadUser() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setToken(preferences.getString(USER_TOKEN_KEY, UserPrefs.USER_DEFAULT.getToken()));
        userPrefs.setRole(UserRole.valueOf(preferences.getString(USER_ROLE_KEY,UserPrefs.USER_DEFAULT.getRole().name())));
        return userPrefs;
    }

    @Override
    public String getUserAuthorizationToken() {
        return "Token " + LoginRequest.CLIENT_ID + ":" + loadUser().getToken();
    }
}
