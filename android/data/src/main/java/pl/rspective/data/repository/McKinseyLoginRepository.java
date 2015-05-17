package pl.rspective.data.repository;

import javax.inject.Inject;

import pl.rspective.data.rest.McKinseyLoginApi;
import pl.rspective.data.rest.model.LoginRequest;
import pl.rspective.data.rest.model.LoginResponse;
import rx.Observable;
import rx.schedulers.Schedulers;

public class McKinseyLoginRepository implements LoginRepository {

    private McKinseyLoginApi api;

    @Inject
    public McKinseyLoginRepository(McKinseyLoginApi api) {
        this.api = api;
    }

    @Override
    public Observable<LoginResponse> userLogin(String login, String password) {
        return api.login(new LoginRequest(login, password))
                .subscribeOn(Schedulers.io());
    }

}
