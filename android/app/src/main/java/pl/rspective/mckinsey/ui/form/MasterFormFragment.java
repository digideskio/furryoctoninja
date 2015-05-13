package pl.rspective.mckinsey.ui.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.form.adapter.FormQuestionPagerAdapter;

public class MasterFormFragment extends Fragment {

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.viewpagertab)
    SmartTabLayout smartTabLayout;

    private FormQuestionPagerAdapter adapter;

    public static MasterFormFragment newInstance() {
        return new MasterFormFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FormQuestionPagerAdapter(getFragmentManager());

        ArrayList<String> questions = new ArrayList<String>() {{
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
        }};

        adapter.updateData(questions);

        viewPager.setAdapter(adapter);

        smartTabLayout.setViewPager(viewPager);
    }
}
