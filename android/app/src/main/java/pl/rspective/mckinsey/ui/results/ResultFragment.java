package pl.rspective.mckinsey.ui.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.Answer;
import pl.rspective.data.entity.Question;
import pl.rspective.data.entity.Survey;
import pl.rspective.data.repository.SurveyRepository;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.architecture.bus.events.SurveyResultsUpdateEvent;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.data.model.SurveySubmitResultType;
import pl.rspective.mckinsey.mvp.presenters.IFormPresenter;
import pl.rspective.mckinsey.mvp.views.IFormView;
import pl.rspective.mckinsey.ui.form.FormQuestionFragment;
import pl.rspective.mckinsey.ui.form.adapter.FormQuestionPagerAdapter;
import rx.functions.Action1;


public class ResultFragment extends Fragment implements IFormView, FormQuestionFragment.QuestionListener {

    @Inject
    Bus bus;
    @Inject
    IFormPresenter formPresenter;
    @Inject
    SurveyRepository surveyRepository;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;
    @InjectView(R.id.viewpagertab)
    SmartTabLayout smartTabLayout;
    @InjectView(R.id.barchart)
    PieChart mChart;

    private FormQuestionPagerAdapter adapter;

    private Survey surveyResult;
    private int idx;

    public static Fragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_form_results, container, false);
        ButterKnife.inject(this, v);

        mChart.setDescription("");
        mChart.setDrawCenterText(true);
        mChart.setCenterTextSize(15);
        mChart.getLegend().setTextSize(15);
        mChart.setNoDataText("Brak danych");

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        formPresenter.onResume(this);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FormQuestionPagerAdapter(getFragmentManager(), this);
        smartTabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                idx = position;
                updateBarChart(idx);
            }
        });

        formPresenter.loadSurvey();

        surveyRepository.fetchSurveyResults()
                .subscribe(new Action1<Survey>() {
                    @Override
                    public void call(Survey surveyResult) {
                        ResultFragment.this.surveyResult = surveyResult;
                        updateBarChart(idx);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void updateBarChart(int idx) {
        if (surveyResult == null) {
            return;
        }

        Question question = surveyResult.getQuestions().get(idx);

        int sum = 0;
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            int count = question.getAnswers().get(i).getCount();
            if (count > 0) {
                entries.add(new Entry(count, i));
                labels.add(String.valueOf("abcdefgh".charAt(i) + ")"));
                sum += question.getAnswers().get(i).getCount();
            }
        }

        PieDataSet ds = new PieDataSet(entries, "");
        ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds.setSliceSpace(3);

        PieData data =  new PieData(labels, ds);
        data.setValueTextSize(15);
        data.setDrawValues(true);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        mChart.highlightValues(null); // undo all highlights
        mChart.clearAnimation();
        mChart.setData(data);
        mChart.highlightValues(null); // undo all highlights
        mChart.setCenterText("Razem:\n" + sum);
        mChart.animateY(500);
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

    }

    @Override
    public void showResultFragment() {

    }

    @Override
    public void showSubmitDialog(SurveySubmitResultType type) {

    }

    @Override
    public void nextQuestion(int position) {

    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void onQuestionUpdate(int number, Question question, Answer answer) {
        formPresenter.updateSurvey(number, question, answer);
    }

    @Subscribe
    public void onSurveyResultsUpdateEvent(SurveyResultsUpdateEvent updateEvent) {
        formPresenter.loadSurvey();
    }
}