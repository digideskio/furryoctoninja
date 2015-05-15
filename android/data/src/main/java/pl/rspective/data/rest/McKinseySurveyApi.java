package pl.rspective.data.rest;

import pl.rspective.data.rest.model.SurveySubmitRequest;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

public interface McKinseySurveyApi {

    @GET("/api/survey")
    Observable<String> fetchSurvey();

    @POST("/api/survey")
    Observable<String> submitSurvey(@Body SurveySubmitRequest submitRequest);

    @GET("/api/survey/results")
    Observable<String> fetchSurveyResults();

    @GET("/api/survey/users")
    Observable<String> fetchSurveyUsers();

}
