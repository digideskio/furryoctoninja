package pl.rspective.mckinsey.data.providers;

import com.yalantis.contextmenu.lib.MenuObject;

import java.util.ArrayList;
import java.util.List;

import pl.rspective.data.rest.model.UserRole;
import pl.rspective.mckinsey.R;

public class McKinseyMenuProvider implements MenuProvider {

    @Override
    public List<MenuObject> provideMenuList(UserRole userRole) {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.ic_close);
        close.setBgResource(R.color.primary_dark);

        MenuObject logout = new MenuObject("Wyloguj");
        logout.setResource(R.drawable.ic_logout);
        logout.setBgResource(R.color.primary_dark);

        menuObjects.add(close);
        menuObjects.add(logout);

        switch (userRole) {
            case USER:
                break;
            case ADMIN:
                MenuObject results = new MenuObject("Wyniki");
                results.setResource(R.drawable.ic_results);
                results.setBgResource(R.color.primary_dark);
                menuObjects.add(results);

                MenuObject users = new MenuObject("Użytkownicy");
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
