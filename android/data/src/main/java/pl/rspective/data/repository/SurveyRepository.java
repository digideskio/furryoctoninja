package pl.rspective.data.repository;

import pl.rspective.data.entity.Survey;
import rx.Observable;

public interface SurveyRepository {

    Observable<Survey> getLatestSurvey();

//    Observable<String> submitSurvey();

//    Observable<String> fetchSurveyResults();

//    Observable<String> fetchSurveyUsers();

}
