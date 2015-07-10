package pl.rspective.data.rest;

import pl.rspective.data.rest.model.SurveySubmitRequest;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

public interface SurveySurveyApi {

    @GET("/api/survey")
    Observable<String> fetchSurvey(@Header("Authorization") String auth);

    @POST("/api/survey")
    Observable<Response> submitSurvey(@Header("Authorization") String auth, @Body SurveySubmitRequest submitRequest);

    @GET("/api/survey/results")
    Observable<String> fetchSurveyResults(@Header("Authorization") String auth);

    @GET("/api/survey/users")
    Observable<String> fetchSurveyUsers(@Header("Authorization") String auth);

}
