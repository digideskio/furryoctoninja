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

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import javax.inject.Inject;

import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.mvp.presenters.IMainPresenter;
import pl.rspective.mckinsey.mvp.views.IMainView;
import pl.rspective.mckinsey.ui.results.ResultFragment;

public class MainActivity extends AbsActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener, IMainView {

    @Inject
    IMainPresenter mainPresenter;

    private DialogFragment menuDialogFragment;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        addFragment(mainPresenter.getStartFragment(), true, R.id.fl_main_fragment_container);
        initMenuItems();

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
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
            case 2:
                addFragment(ResultFragment.newInstance(), false, R.id.fl_main_fragment_container);
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
}
