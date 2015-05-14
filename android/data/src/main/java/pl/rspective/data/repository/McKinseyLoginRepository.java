package pl.rspective.data.repository;

import javax.inject.Inject;

import pl.rspective.data.rest.McKinseyLoginApi;
import retrofit.client.Response;
import rx.Observable;
import rx.schedulers.Schedulers;

public class McKinseyLoginRepository implements LoginRepository {

    private McKinseyLoginApi api;

    @Inject
    public McKinseyLoginRepository(McKinseyLoginApi api) {
        this.api = api;
    }

    @Override
    public Observable<Response> userLogin(String login, String password) {
        return api.login(login, password)
                .subscribeOn(Schedulers.io());
    }

}
