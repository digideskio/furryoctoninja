package pl.rspective.mckinsey.ui.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

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


public class ResultFragment extends Fragment implements OnChartGestureListener, IFormView, FormQuestionFragment.QuestionListener {

    @Inject
    Bus bus;

    @Inject
    IFormPresenter formPresenter;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.viewpagertab)
    SmartTabLayout smartTabLayout;

    @InjectView(R.id.barchart)
    BarChart mChart;

    private FormQuestionPagerAdapter adapter;

    @Inject
    SurveyRepository surveyRepository;

    private Survey surveyResult;
    private int idx;

    public static Fragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_simple_bar, container, false);
        View v = inflater.inflate(R.layout.fragment_form_results, container, false);

        ButterKnife.inject(this, v);

        // create a new chart object
//        mChart = new BarChart(getActivity());
        mChart.setDescription("");
        mChart.setOnChartGestureListener(this);
        mChart.setMarkerView(new MyMarkerView(getActivity(), R.layout.custom_marker_view));
        mChart.setHighlightIndicatorEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        //mChart.setData(updateBarChart(0));

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        // programatically add the chart
//        FrameLayout parent = (FrameLayout) v.findViewById(R.id.parentLayout);
//        parent.addView(mChart);



        return v;
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
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
        Question question = surveyResult.getQuestions().get(idx);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            entries.add(new BarEntry(question.getAnswers().get(i).getCount(), i, String.valueOf("abcdefgh".charAt(i))));
            labels.add(String.valueOf("abcdefgh".charAt(i)));
        }

        BarDataSet ds = new BarDataSet(entries, "hello");
        ds.setColors(ColorTemplate.LIBERTY_COLORS);

        ArrayList<BarDataSet> sets = new ArrayList<>();
        sets.add(ds);
        BarData data =  new BarData(labels, sets);
        mChart.setData(data);
    }

    private int findMaxAnswersCount(Question question) {
        int max = 0;
        for (Answer answer : question.getAnswers()) {
            if (answer.getCount() > max) {
                max = answer.getCount();
            }
        }
        return max;
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