package pl.rspective.mckinsey.mvp.presenters;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import pl.rspective.data.entity.Answer;
import pl.rspective.data.entity.Question;
import pl.rspective.data.entity.Survey;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.data.local.model.StorageType;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.data.repository.UserRepository;
import pl.rspective.data.rest.SurveyResultCode;
import pl.rspective.data.rest.model.SurveySubmitRequest;
import pl.rspective.data.rest.model.UserRole;
import pl.rspective.mckinsey.architecture.bus.events.AnswerUpdateEvent;
import pl.rspective.mckinsey.data.model.SurveySubmitResultType;
import pl.rspective.mckinsey.mvp.views.IFormView;
import retrofit.client.Response;
import rx.Subscription;
import rx.functions.Action1;

public class FormPresenter implements IFormPresenter {

    private static final String TAG = "FormPresenter";

    private Bus bus;
    private LocalPreferences localPreferences;
    private UserRepository userRepository;
    private SurveyRepository surveyRepository;
    private SurveyLocalStorage<String> localStorage;

    private IFormView formView;

    private Subscription subscriptionSurveyLoad;
    private Subscription subscriptionSurveySubmit;
    private Gson gson;
    private Survey survey;

    @Inject
    public FormPresenter(Bus bus, SurveyRepository surveyRepository, UserRepository userRepository, SurveyLocalStorage<String> localStorage, LocalPreferences localPreferences) {
        this.bus = bus;
        this.localPreferences = localPreferences;
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
        this.localStorage = localStorage;

        this.gson = new GsonBuilder().create();
    }

    @Override
    public void updateSurvey(int number, Question question, Answer answer) {

        // no need for any action if ADMIN
        if (userRepository.loadUser().getRole() == UserRole.ADMIN) {
            return;
        }

        // no need for any action if already submitted
        if(survey.isSubmited()) {
            return;
        }

        question.setUserAnswerId(answer.getId());

        for(Answer a : question.getAnswers()) {
            if(a.getId() == answer.getId()) {
                a.setSelected(true);
            } else {
                a.setSelected(false);
            }
        }

        survey.getQuestions().set(number, question);

        boolean isReadyToSend = validateSurvey(survey);
        survey.setReady(isReadyToSend);

        if(isReadyToSend) {//FIXME find better place for this part
            formView.showSubmitButton();
        }

        storeSurvey();
        formView.nextQuestion(number);

        bus.post(new AnswerUpdateEvent(survey.getQuestions().get(number)));
    }

    private void storeSurvey() {
        String surveyJson = gson.toJson(survey);
        localStorage.clear(StorageType.SURVEY);
        localStorage.save(StorageType.SURVEY, surveyJson);
    }

    private boolean validateSurvey(Survey survey) {
        boolean readyToSend = true;

        for(Question question : survey.getQuestions()) {
            readyToSend = readyToSend && question.getUserAnswerId() > 0;

            if(!readyToSend) {
                break;
            }
        }

        return readyToSend;
    }

    @Override
    public void loadSurvey() {
        subscriptionSurveyLoad = surveyRepository.fetchSurvey(!localPreferences.isSurveyLoaded())
                .subscribe(new Action1<Survey>() {
                    @Override
                    public void call(Survey survey) {
                        localPreferences.setSurveyLoaded(false);

                        FormPresenter.this.survey = survey;

                        if(survey.isSubmited()) {
                            formView.showResultFragment();
                        }

                        formView.updateUi(survey);

                        if(survey.isReady() && !survey.isSubmited()) {
                            formView.showSubmitButton();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    @Override
    public void submitSurvey() {
        SurveySubmitRequest submitRequest = new SurveySubmitRequest(survey.getId(), survey.getCreatedDate());

        for(Question q : survey.getQuestions()) {
            submitRequest.addAnswer(q.getId(), q.getUserAnswerId());
        }

        subscriptionSurveySubmit = surveyRepository.submitSurvey(submitRequest)
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {
                        handleSurveySubmitResponse(response);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        formView.showSubmitDialog(SurveySubmitResultType.SURVEY_ERROR);
                    }
                });
    }

    @Override
    public void resetSurvey() {
        localPreferences.setSurveyLoaded(true);
        localStorage.clear(StorageType.SURVEY);
        loadSurvey();
    }

    private void handleSurveySubmitResponse(Response response) {
        switch(response.getStatus()) {
            case SurveyResultCode.SURVEY_CHANGED_RESPONSE:
                Log.d(TAG, "205 - survey was changed");
                formView.showSubmitDialog(SurveySubmitResultType.SURVEY_WAS_CHANGED);
                return;
            case SurveyResultCode.SURVEY_ALREADY_SUBMITED_RESPONSE:
                Log.d(TAG, "409 - survey already sent");
                formView.showSubmitDialog(SurveySubmitResultType.SURVEY_ALREADY_SEND);
                return;
            case SurveyResultCode.SURVEY_OK_RESPONSE:
                Log.d(TAG, "200 - everything is fine");
                survey.setSubmited(true);
                storeSurvey();
                formView.showSubmitDialog(SurveySubmitResultType.SURVEY_OK);
                return;
        }
    }

    @Override
    public void onResume(IFormView view) {
        this.formView = view;
    }

    @Override
    public void onDestroy() {
        this.formView = null;

        if(subscriptionSurveyLoad != null) {
            subscriptionSurveyLoad.unsubscribe();
        }

        if(subscriptionSurveySubmit != null) {
            subscriptionSurveySubmit.unsubscribe();
        }
    }
}
