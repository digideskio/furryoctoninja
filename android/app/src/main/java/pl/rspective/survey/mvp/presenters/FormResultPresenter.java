package pl.rspective.survey.mvp.presenters;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.repository.ISurveyRepository;
import pl.rspective.survey.mvp.views.IFormResultView;
import rx.Subscription;
import rx.functions.Action1;

public class FormResultPresenter implements IFormResultPresenter {

    private IFormPresenter formPresenter;
    private ISurveyRepository ISurveyRepository;
    private Subscription subscriptionSurveyResult;

    private IFormResultView view;

    @Inject
    public FormResultPresenter(IFormPresenter formPresenter, ISurveyRepository ISurveyRepository) {
        this.formPresenter = formPresenter;
        this.ISurveyRepository = ISurveyRepository;
    }

    @Override
    public void loadSurveyResults() {
        subscriptionSurveyResult = ISurveyRepository.fetchSurveyResults()
                .subscribe(new Action1<Survey>() {
                    @Override
                    public void call(Survey surveyResult) {
                        view.updateUi(surveyResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void restartSurvey() {
        formPresenter.resetSurvey();
        view.showFormFragment();
    }

    @Override
    public void onResume(IFormResultView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        this.view = null;

        if(subscriptionSurveyResult != null) {
            subscriptionSurveyResult.unsubscribe();
        }
    }
}
