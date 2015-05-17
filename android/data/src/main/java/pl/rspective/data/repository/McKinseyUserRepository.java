package pl.rspective.data.repository;

import pl.rspective.data.entity.UserPrefs;
import pl.rspective.data.rest.model.LoginRequest;

public class McKinseyUserRepository implements UserRepository {

    private UserPrefs userPrefs;

    public McKinseyUserRepository() {
        this.userPrefs = new UserPrefs();
    }

    @Override
    public void saveUser(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    @Override
    public UserPrefs loadUser() {
        return userPrefs;
    }

    @Override
    public String getUserAuthorizationToken() {
        return "Token " + LoginRequest.CLIENT_ID + ":" +userPrefs.getToken();
    }
}
