package pl.rspective.mckinsey.ui.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.users.adapter.UsersFormPagerAdapter;

public class MasterUserFragment extends Fragment {

    @InjectView(R.id.view_pager_users)
    ViewPager viewPager;

    @InjectView(R.id.view_pager_tab_users)
    SmartTabLayout smartTabLayout;

    private UsersFormPagerAdapter adapter;

    public static MasterUserFragment newInstance() {
        return new MasterUserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_users_master, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new UsersFormPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }
}
