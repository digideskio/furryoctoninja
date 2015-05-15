package pl.rspective.mckinsey.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.local.model.StorageType;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.mckinsey.mvp.views.IFormView;
import rx.Subscription;
import rx.functions.Action1;

public class FormPresenter implements IFormPresenter {

    private LocalPreferences localPreferences;
    private SurveyRepository surveyRepository;
    private SurveyLocalStorage<String> localStorage;

    private IFormView formView;

    private Subscription subscription;
    private Gson gson;
    private Survey survey;

    @Inject
    public FormPresenter(SurveyRepository surveyRepository, SurveyLocalStorage<String> localStorage, LocalPreferences localPreferences) {
        this.localPreferences = localPreferences;
        this.surveyRepository = surveyRepository;
        this.localStorage = localStorage;

        this.gson = new GsonBuilder().create();
    }

    @Override
    public void updateSurvey(Survey survey) {
        String surveyJson = gson.toJson(survey);
        localStorage.clear(StorageType.SURVEY);
        localStorage.save(StorageType.SURVEY, surveyJson);
    }

    @Override
    public void loadSurvey() {

        subscription = surveyRepository.fetchSurvey(!localPreferences.isUserFirstLogin())
                .subscribe(new Action1<Survey>() {
                    @Override
                    public void call(Survey survey) {
                        localPreferences.setFirstUserLogin();

                        FormPresenter.this.survey = survey;
                        formView.updateUi(survey);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void onResume(IFormView view) {
        this.formView = view;

    }

    @Override
    public void onDestroy() {
        this.formView = null;

        if(subscription != null) {
            subscription.unsubscribe();
        }
    }
}
