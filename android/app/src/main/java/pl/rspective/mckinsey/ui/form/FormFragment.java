package pl.rspective.mckinsey.ui.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.form.adapter.FormQuestionPagerAdapter;

public class FormFragment extends Fragment {

    @InjectView(R.id.view_pager)
    ScrollerViewPager viewPager;

    @InjectView(R.id.indicator)
    SpringIndicator springIndicator;

    private FormQuestionPagerAdapter adapter;

    public static FormFragment newInstance() {
        return new FormFragment();
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
        adapter.updateData(new ArrayList<String>() {{
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
            add("test 1");
        }});

        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();
        springIndicator.setViewPager(viewPager);
    }
}
