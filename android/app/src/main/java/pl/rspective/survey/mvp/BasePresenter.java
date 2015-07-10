package pl.rspective.survey.mvp;

public interface BasePresenter<T extends BaseView> {

    void onResume(T view);

    void onDestroy();

}
