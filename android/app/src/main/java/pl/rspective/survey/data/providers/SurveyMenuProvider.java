package pl.rspective.survey.data.providers;

import android.content.res.Resources;

import com.yalantis.contextmenu.lib.MenuObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.rspective.data.rest.model.UserRole;
import pl.rspective.survey.R;
import pl.rspective.survey.dagger.Injector;

public class SurveyMenuProvider implements MenuProvider {

    @Inject
    Resources resources;

    public SurveyMenuProvider() {
        Injector.inject(this);
    }

    @Override
    public List<MenuObject> provideMenuList(UserRole userRole) {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.ic_close);
        close.setBgResource(R.color.primary_dark);

        MenuObject logout = new MenuObject(resources.getString(R.string.menu_label_logout));
        logout.setResource(R.drawable.ic_logout);
        logout.setBgResource(R.color.primary_dark);

        menuObjects.add(close);
        menuObjects.add(logout);

        switch (userRole) {
            case USER:
                break;
            case ADMIN:
                MenuObject results = new MenuObject(resources.getString(R.string.menu_label_results));
                results.setResource(R.drawable.ic_results);
                results.setBgResource(R.color.primary_dark);
                menuObjects.add(results);

                MenuObject users = new MenuObject(resources.getString(R.string.menu_label_users));
                users.setResource(R.drawable.ic_users);
                users.setBgResource(R.color.primary_dark);
                menuObjects.add(users);
                break;
            default:
                break;
        }

        return menuObjects;
    }

}
