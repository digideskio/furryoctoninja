package pl.rspective.data.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.rest.McKinseySurveyApi;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class McKinseySurveyRepository implements SurveyRepository {

    private McKinseySurveyApi api;
    private Gson gson;

    @Inject
    public McKinseySurveyRepository(McKinseySurveyApi api) {
        this.api = api;

        this.gson = new GsonBuilder().create();
    }

    @Override
    public Observable<Survey> getLatestSurvey() {
        return api.fetchSurvey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Survey>() {
                    @Override
                    public Survey call(String jsonData) {
                        return gson.fromJson(jsonData, Survey.class);
                    }
                });
    }
}
