package pl.rspective.survey.ui.users;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.User;
import pl.rspective.survey.R;
import pl.rspective.survey.architecture.bus.events.SurveyResultsUpdateEvent;
import pl.rspective.survey.dagger.Injector;
import pl.rspective.survey.mvp.presenters.IUserPresenter;
import pl.rspective.survey.mvp.views.IUserView;
import pl.rspective.survey.ui.users.adapter.UsersFormPagerAdapter;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class MasterUserFragment extends Fragment implements IUserView {

    public static interface UserUpdateListListener {

        void onUserListReceived(List<User> users);

    }

    @Inject
    Bus bus;

    @Inject
    IUserPresenter presenter;

    @InjectView(R.id.view_pager_users)
    ViewPager viewPager;

    @InjectView(R.id.view_pager_tab_users)
    SmartTabLayout smartTabLayout;

    private UsersFormPagerAdapter adapter;

    public static MasterUserFragment newInstance() {
        return new MasterUserFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        presenter.onResume(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_users_master, container, false);
        ButterKnife.inject(this, view);
        typeface(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new UsersFormPagerAdapter(getFragmentManager());
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

        presenter.refreshUserList();
    }

    @Override
    public void updateUserList(List<User> users) {
        ((UserUpdateListListener)adapter.getRegisteredFragment(0)).onUserListReceived(users);
        ((UserUpdateListListener)adapter.getRegisteredFragment(1)).onUserListReceived(users);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Subscribe
    public void onSurveyUserListUpdateEvent(SurveyResultsUpdateEvent updateEvent) {
        presenter.refreshUserList();
    }

}
