package pl.rspective.data.rest;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface McKinseyLoginApi {

    @FormUrlEncoded
    @POST("/login")
    Observable<Response> login(@Field("login") String login, @Field("passsword") String password);

}
