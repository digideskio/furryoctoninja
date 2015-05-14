package pl.rspective.data.repository;

import retrofit.client.Response;
import rx.Observable;

public interface LoginRepository {

    Observable<Response> userLogin(String login, String password);

}
