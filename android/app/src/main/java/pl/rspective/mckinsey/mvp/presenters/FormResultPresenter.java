package pl.rspective.mckinsey.mvp.presenters;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.mckinsey.mvp.views.IFormResultView;
import rx.Subscription;
import rx.functions.Action1;

public class FormResultPresenter implements IFormResultPresenter {

    private Bus bus;
    private SurveyRepository surveyRepository;
    private Subscription subscriptionSurveyResult;

    private IFormResultView view;

    @Inject
    public FormResultPresenter(Bus bus, SurveyRepository surveyRepository) {
        this.bus = bus;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public void loadSurveyResults() {
        subscriptionSurveyResult = surveyRepository.fetchSurveyResults()
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
