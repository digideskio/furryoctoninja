package pl.rspective.mckinsey.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import javax.inject.Inject;

import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pl.rspective.data.rest.NetworkAction;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.mvp.presenters.IMainPresenter;
import pl.rspective.mckinsey.mvp.views.IMainView;
import pl.rspective.mckinsey.ui.results.ResultFragment;
import pl.rspective.mckinsey.ui.users.MasterUserFragment;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class MainActivity extends AbsActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener, IMainView, Observer<NetworkAction> {

    @Inject
    PublishSubject<NetworkAction> networkActivity;

    @Inject
    IMainPresenter mainPresenter;

    @InjectView(R.id.fl_loading_container)
    FrameLayout loadingContainer;

    private Subscription subscription;

    private DialogFragment menuDialogFragment;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        mainPresenter.onResume(this);
        mainPresenter.checkAppEventStatus();

        addFragment(mainPresenter.getStartFragment(), true, R.id.fl_main_fragment_container);
        initMenuItems();

        subscription = networkActivity.subscribe(this);
    }

    private void initMenuItems() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        menuParams.setMenuObjects(mainPresenter.getMainMenuItems());
        menuParams.setClosableOutside(false);

        menuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                if (getSupportFragmentManager().findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    menuDialogFragment.show(getSupportFragmentManager(), ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (menuDialogFragment != null && menuDialogFragment.isAdded()) {
            menuDialogFragment.dismiss();
        } else{
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                mainPresenter.userLogout();

                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
            case 2:
                addFragment(ResultFragment.newInstance(), false, R.id.fl_main_fragment_container);
                break;
            case 3:
                addFragment(MasterUserFragment.newInstance(), false, R.id.fl_main_fragment_container);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {}

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(NetworkAction networkAction) {
        switch (networkAction) {
            case HTTP_REQUEST_START:
//                loadingContainer.setVisibility(View.VISIBLE);
                break;

            case HTTP_REQUEST_END:
//                loadingContainer.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void showSurveyReloadDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Nowa ankieta!")
                .setContentText("Ankieta została przeładowana")
                .setCustomImage(R.drawable.ic_launcher)
                .show();
    }
}
