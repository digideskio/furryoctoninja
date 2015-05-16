package pl.rspective.data.repository;

import java.util.List;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.entity.SurveyResult;
import pl.rspective.data.entity.User;
import rx.Observable;

public interface SurveyRepository {

    Observable<Survey> fetchSurvey(boolean useCache);

//    Observable<String> submitSurvey();

    Observable<SurveyResult> fetchSurveyResults();

    Observable<List<User>> fetchSurveyUsers();

}
