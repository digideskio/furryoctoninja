package pl.rspective.data.rest;

import pl.rspective.data.rest.model.LoginRequest;
import pl.rspective.data.rest.model.LoginResponse;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface McKinseyLoginApi {

    @POST("/api/auth/login")
    Observable<LoginResponse> login(@Body LoginRequest request);

}
