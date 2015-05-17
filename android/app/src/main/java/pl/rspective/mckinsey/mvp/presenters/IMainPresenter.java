package pl.rspective.mckinsey.mvp.presenters;

import android.support.v4.app.Fragment;

import com.yalantis.contextmenu.lib.MenuObject;

import java.util.List;

import pl.rspective.mckinsey.mvp.BasePresenter;
import pl.rspective.mckinsey.mvp.views.IMainView;

public interface IMainPresenter extends BasePresenter<IMainView> {

    Fragment getStartFragment();

    List<MenuObject> getMainMenuItems();

}
