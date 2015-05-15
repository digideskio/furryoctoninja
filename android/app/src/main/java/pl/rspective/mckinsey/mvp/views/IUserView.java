package pl.rspective.mckinsey.mvp.views;

import java.util.List;

import pl.rspective.data.entity.User;
import pl.rspective.mckinsey.mvp.BaseView;

public interface IUserView extends BaseView {

    void updateUserList(List<User>users);

}
