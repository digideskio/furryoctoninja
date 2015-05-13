package pl.rspective.data.rest;

import retrofit.client.Response;
import retrofit.http.POST;
import rx.Observable;

public interface McKinseyLoginApi {

    @POST("/login")
    Observable<Response> login(String login, String password);

}
