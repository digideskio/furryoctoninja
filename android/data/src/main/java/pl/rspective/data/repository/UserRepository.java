package pl.rspective.data.repository;

import pl.rspective.data.entity.UserPrefs;

public interface UserRepository {

    void saveUser(UserPrefs userPrefs);

    UserPrefs loadUser();

    String getUserAuthorizationToken();

}
