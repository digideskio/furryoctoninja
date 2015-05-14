package pl.rspective.data.rest;

import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

public interface McKinseyGeneralApi {

    @GET("/api/survey")
    Observable<String> fetchSurvey();

    @POST("/api/survey ")
    Observable<String> submitSurvey();

    @GET("/api/survey/results")
    Observable<String> fetchSurveyResults();

    @GET("/api/survey/users")
    Observable<String> fetchSurveyUsers();

}
