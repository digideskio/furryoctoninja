package pl.rspective.survey.mvp.presenters;

import android.support.v4.app.Fragment;

import com.yalantis.contextmenu.lib.MenuObject;

import java.util.List;

import pl.rspective.survey.mvp.BasePresenter;
import pl.rspective.survey.mvp.views.IMainView;

public interface IMainPresenter extends BasePresenter<IMainView> {

    Fragment getStartFragment();

    List<MenuObject> getMainMenuItems();

    void checkAppEventStatus();

    void userLogout();

    boolean isAdmin();

}
