package pl.rspective.data.repository;

import pl.rspective.data.rest.model.LoginResponse;
import rx.Observable;

public interface LoginRepository {

    Observable<LoginResponse> userLogin(String login, String password);

}
