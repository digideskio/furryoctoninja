package pl.rspective.survey.mvp.views;

import java.util.List;

import pl.rspective.data.entity.User;
import pl.rspective.survey.mvp.BaseView;

public interface IUserView extends BaseView {

    void updateUserList(List<User>users);

}
