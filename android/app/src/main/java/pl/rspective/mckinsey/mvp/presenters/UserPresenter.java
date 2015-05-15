package pl.rspective.mckinsey.mvp.presenters;

import java.util.List;

import javax.inject.Inject;

import pl.rspective.data.entity.User;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.mckinsey.mvp.views.IUserView;
import rx.Subscription;
import rx.functions.Action1;

public class UserPresenter implements IUserPresenter {

    private IUserView view;
    private SurveyRepository surveyRepository;
    private Subscription subscription;

    @Inject
    public UserPresenter(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public void onResume(final IUserView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        this.view = null;

        if(subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void refreshUserList() {
        this.subscription = surveyRepository.fetchSurveyUsers()
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        view.updateUserList(users);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
