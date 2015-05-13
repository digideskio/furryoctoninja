package pl.rspective.mckinsey.mvp;

public interface BasePresenter<T extends BaseView> {

    void onResume(T view);

    void onDestroy();

}
