package pl.rspective.data.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.entity.SurveyResult;
import pl.rspective.data.entity.User;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.local.model.StorageType;
import pl.rspective.data.rest.McKinseySurveyApi;
import pl.rspective.data.rest.model.UserListResponse;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class McKinseySurveyRepository implements SurveyRepository {

    private UserRepository userRepository;
    private SurveyLocalStorage<String> surveyStorage;
    private McKinseySurveyApi api;
    private Gson gson;

    @Inject
    public McKinseySurveyRepository(UserRepository userRepository, SurveyLocalStorage<String> surveyStorage, McKinseySurveyApi api) {
        this.userRepository = userRepository;
        this.surveyStorage = surveyStorage;
        this.api = api;

        this.gson = new GsonBuilder().create();
    }

    @Override
    public Observable<Survey> fetchSurvey(boolean useCache) {
        if(useCache) {
            return Observable.create(new Observable.OnSubscribe<Survey>() {
                @Override
                public void call(Subscriber<? super Survey> subscriber) {
                    subscriber.onNext(gson.fromJson(surveyStorage.load(StorageType.SURVEY), Survey.class));
                    subscriber.onCompleted();
                }
            });
        } else {
            return api.fetchSurvey(userRepository.getUserAuthorizationToken())
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
    }

    @Override
    public Observable<SurveyResult> fetchSurveyResults() {
        return api.fetchSurveyResults(userRepository.getUserAuthorizationToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, SurveyResult>() {
                    @Override
                    public SurveyResult call(String jsonData) {
                        return gson.fromJson(jsonData, SurveyResult.class);
                    }
                });
    }

    @Override
    public Observable<List<User>> fetchSurveyUsers() {
        return api.fetchSurveyUsers(userRepository.getUserAuthorizationToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        return surveyStorage.load(StorageType.USERS);
                    }
                })
                .map(new Func1<String, List<User>>() {
                    @Override
                    public List<User> call(String jsonData) {
                        surveyStorage.clear(StorageType.USERS);
                        surveyStorage.save(StorageType.USERS, jsonData);
                        return gson.fromJson(jsonData, UserListResponse.class).getUsers();
//                        return gson.fromJson(jsonData, new TypeToken<List<pl.rspective.data.entity.User>>(){}.getType());
                    }
                });
    }


}
