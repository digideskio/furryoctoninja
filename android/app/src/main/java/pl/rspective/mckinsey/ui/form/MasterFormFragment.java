package pl.rspective.mckinsey.ui.form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.Question;
import pl.rspective.data.entity.Survey;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.mvp.presenters.IFormPresenter;
import pl.rspective.mckinsey.mvp.views.IFormView;
import pl.rspective.mckinsey.ui.form.adapter.FormQuestionPagerAdapter;

public class MasterFormFragment extends Fragment implements IFormView, FormQuestionFragment.QuestionListener {

    @Inject
    IFormPresenter formPresenter;

    @InjectView(R.id.btn_survey_submit)
    Button btnSurveySubmit;

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

        formPresenter.onResume(this);
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

        adapter = new FormQuestionPagerAdapter(getFragmentManager(), this);
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

        formPresenter.loadSurvey();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        formPresenter.onDestroy();
    }

    @Override
    public void updateUi(Survey survey) {
        if(survey == null || survey.getQuestions() == null) {
            return;
        }

        adapter.updateData(survey.getQuestions());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public void showSubmitButton() {
        btnSurveySubmit.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceInUp)
                .duration(200)
                .playOn(btnSurveySubmit);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void onQuestionUpdate(int number, Question question) {
        formPresenter.updateSurvey(number, question);
    }

}
