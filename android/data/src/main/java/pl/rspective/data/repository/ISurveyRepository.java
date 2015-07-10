package pl.rspective.data.repository;

import java.util.List;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.entity.User;
import pl.rspective.data.rest.model.SurveySubmitRequest;
import retrofit.client.Response;
import rx.Observable;

public interface ISurveyRepository {

    Observable<Survey> fetchSurvey(boolean useCache);

    Observable<Response> submitSurvey(SurveySubmitRequest submitRequest);

    Observable<Survey> fetchSurveyResults();

    Observable<List<User>> fetchSurveyUsers();

}
