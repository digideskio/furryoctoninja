package pl.rspective.data.repository;

import javax.inject.Inject;

import pl.rspective.data.rest.SurveyLoginApi;
import pl.rspective.data.rest.model.LoginRequest;
import pl.rspective.data.rest.model.LoginResponse;
import rx.Observable;
import rx.schedulers.Schedulers;

public class SurveyLoginRepository implements LoginRepository {

    private SurveyLoginApi api;

    @Inject
    public SurveyLoginRepository(SurveyLoginApi api) {
        this.api = api;
    }

    @Override
    public Observable<LoginResponse> userLogin(String login, String password) {
        return api.login(new LoginRequest(login, password))
                .subscribeOn(Schedulers.io());
    }

}
