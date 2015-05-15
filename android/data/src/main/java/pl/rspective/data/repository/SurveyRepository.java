package pl.rspective.data.repository;

import java.util.List;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.entity.User;
import rx.Observable;

public interface SurveyRepository {

    Observable<Survey> fetchSurvey(boolean useCache);

//    Observable<String> submitSurvey();

//    Observable<String> fetchSurveyResults();

    Observable<List<User>> fetchSurveyUsers();

}
