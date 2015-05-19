package pl.rspective.mckinsey.ui.results;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

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

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.viewpagertab)
    SmartTabLayout smartTabLayout;

    private FormQuestionPagerAdapter adapter;

    @Inject
    SurveyRepository surveyRepository;

    private Survey surveyResult;
    private int idx;

    public static ResultFragment newInstance() {
        return new ResultFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_results, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FormQuestionPagerAdapter(getFragmentManager(), this);
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        smartTabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(mBarTooltip != null) {
                    dismissBarTooltip(-1, -1, null);
                }

                idx = position;
                updateBarChart(idx);
            }
        });

        initBarChart();

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

    @InjectView(R.id.barchart)
    BarChartView mBarChart;
    private Paint mBarGridPaint;
    private TextView mBarTooltip;

    private final TimeInterpolator enterInterpolator = new DecelerateInterpolator(1.5f);
    private final TimeInterpolator exitInterpolator = new AccelerateInterpolator();

    private final OnEntryClickListener barEntryListener = new OnEntryClickListener(){
        @Override
        public void onClick(int setIndex, int entryIndex, Rect rect) {
            if(mBarTooltip == null)
                showBarTooltip(setIndex, entryIndex, rect);
            else
                dismissBarTooltip(setIndex, entryIndex, rect);
        }
    };

    private final View.OnClickListener barClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(mBarTooltip != null)
                dismissBarTooltip(-1, -1, null);
        }
    };

    private final View.OnTouchListener barChartDelegateSwipeListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mBarChart.onTouchEvent(motionEvent);
            return viewPager.dispatchTouchEvent(motionEvent);
        }
    };

	/*------------------------------------*
	 *              BARCHART              *
	 *------------------------------------*/

    private void initBarChart(){
        mBarChart.setOnEntryClickListener(barEntryListener);
        mBarChart.setOnClickListener(barClickListener);
        mBarChart.setOnTouchListener(barChartDelegateSwipeListener);

        mBarGridPaint = new Paint();
        mBarGridPaint.setColor(this.getResources().getColor(R.color.bar_grid));
        mBarGridPaint.setStyle(Paint.Style.STROKE);
        mBarGridPaint.setAntiAlias(true);
        mBarGridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
    }

    private void updateBarChart(int idx) {
        mBarChart.reset();

        Question question = surveyResult.getQuestions().get(idx);

        BarSet barSet = new BarSet();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            Bar bar = new Bar(String.valueOf("abcdefgh".charAt(i)), question.getAnswers().get(i).getCount());
            bar.setColor(this.getResources().getColor(R.color.bar_fill));
            barSet.addBar(bar);
        }
        mBarChart.addData(barSet);

        mBarChart.setSetSpacing(Tools.fromDpToPx(3));
        mBarChart.setBarSpacing(Tools.fromDpToPx(14));

        int max = findMaxAnswersCount(question);

        if (max % 2 == 1) {
            max += 1; // avoid IllegalArgumentException: Step value must be a divisor of distance between minValue and maxValue
        }

        mBarChart.setBorderSpacing(0)
                .setAxisBorderValues(0, max + (max % 2), 2) //
                .setGrid(BarChartView.GridType.FULL, mBarGridPaint)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .show(new Animation());
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


    @SuppressLint("NewApi")
    private void showBarTooltip(int setIndex, int entryIndex, Rect rect){
        mBarTooltip = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.bar_tooltip, null);
        mBarTooltip.setText(Integer.toString(surveyResult.getQuestions().get(idx).getAnswers().get(entryIndex).getCount()));
        // FIXME how to eliminate dependency to idx?

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(rect.width(), rect.height());
        layoutParams.leftMargin = rect.left;
        layoutParams.topMargin = rect.top;
        mBarTooltip.setLayoutParams(layoutParams);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
            mBarTooltip.setAlpha(0);
            mBarTooltip.setScaleY(0);
            mBarTooltip.animate()
                    .setDuration(200)
                    .alpha(1)
                    .scaleY(1)
                    .setInterpolator(enterInterpolator);
        }

        mBarChart.showTooltip(mBarTooltip);
    }


    @SuppressLint("NewApi")
    private void dismissBarTooltip(final int setIndex, final int entryIndex, final Rect rect){

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            mBarTooltip.animate()
                    .setDuration(100)
                    .scaleY(0)
                    .alpha(0)
                    .setInterpolator(exitInterpolator).withEndAction(new Runnable(){
                @Override
                public void run() {
                    mBarChart.removeView(mBarTooltip);
                    mBarTooltip = null;
                    if(entryIndex != -1)
                        showBarTooltip(setIndex, entryIndex, rect);
                }
            });
        }else{
            mBarChart.dismissTooltip(mBarTooltip);
            mBarTooltip = null;
            if(entryIndex != -1)
                showBarTooltip(setIndex, entryIndex, rect);
        }
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
        Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
        formPresenter.loadSurvey();
    }
}
