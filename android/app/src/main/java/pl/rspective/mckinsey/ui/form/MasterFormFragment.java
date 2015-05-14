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

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.Survey;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.ui.form.adapter.FormQuestionPagerAdapter;
import rx.functions.Action1;

public class MasterFormFragment extends Fragment {

    @Inject
    SurveyRepository surveyRepository;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.viewpagertab)
    SmartTabLayout smartTabLayout;

    private FormQuestionPagerAdapter adapter;

    public static MasterFormFragment newInstance() {
        return new MasterFormFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
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

        surveyRepository.getLatestSurvey()
                .subscribe(new Action1<Survey>() {
                    @Override
                    public void call(Survey survey) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
