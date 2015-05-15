package pl.rspective.data.repository;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.entity.User;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.local.model.StorageType;
import pl.rspective.data.rest.McKinseySurveyApi;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class McKinseySurveyRepository implements SurveyRepository {

    private SurveyLocalStorage<String> surveyStorage;
    private McKinseySurveyApi api;
    private Gson gson;

    @Inject
    public McKinseySurveyRepository(SurveyLocalStorage<String> surveyStorage, McKinseySurveyApi api) {
        this.surveyStorage = surveyStorage;
        this.api = api;

        this.gson = new GsonBuilder().create();
    }

    @Override
    public Observable<Survey> getLatestSurvey() {
        return api.fetchSurvey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        return surveyStorage.load(StorageType.SURVEY);
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        surveyStorage.clear(StorageType.SURVEY);
                        surveyStorage.save(StorageType.SURVEY, s);
                        return s;
                    }
                })
                .map(new Func1<String, Survey>() {
                    @Override
                    public Survey call(String jsonData) {
                        return gson.fromJson(jsonData, Survey.class);
                    }
                });
    }

    @Override
    public Observable<List<User>> fetchSurveyUsers() {
        return api.fetchSurveyUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        return null; //TODO load user list from cache
                    }
                })
                .map(new Func1<String, List<User>>() {
                    @Override
                    public List<User> call(String jsonData) {
                        return gson.fromJson(jsonData, new TypeToken<List<User>>(){}.getType());
                    }
                });
    }
}
