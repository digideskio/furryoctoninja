package pl.rspective.mckinsey.mvp.presenters;

import javax.inject.Inject;

import pl.rspective.data.entity.Survey;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.mckinsey.mvp.views.IFormView;
import rx.Subscription;
import rx.functions.Action1;

public class FormPresenter implements IFormPresenter {

    private SurveyRepository surveyRepository;
    private IFormView formView;

    private Subscription subscription;

    @Inject
    public FormPresenter(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public void loadSurvey() {
        subscription = surveyRepository.getLatestSurvey()
                .subscribe(new Action1<Survey>() {
                    @Override
                    public void call(Survey survey) {
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
