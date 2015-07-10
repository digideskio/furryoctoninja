package pl.rspective.survey.data.providers;

import com.yalantis.contextmenu.lib.MenuObject;

import java.util.List;

import pl.rspective.data.rest.model.UserRole;

public interface MenuProvider {

    List<MenuObject> provideMenuList(UserRole userRole);

}
