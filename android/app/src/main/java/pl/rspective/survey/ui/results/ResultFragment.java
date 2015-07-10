package pl.rspective.survey.ui.results;

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
import cn.pedant.SweetAlert.SweetAlertDialog;
import pl.rspective.data.entity.Question;
import pl.rspective.data.entity.Survey;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.survey.R;
import pl.rspective.survey.architecture.bus.events.SurveyRestartEvent;
import pl.rspective.survey.architecture.bus.events.SurveyResultsUpdateEvent;
import pl.rspective.survey.dagger.Injector;
import pl.rspective.survey.data.model.AppEventStatus;
import pl.rspective.survey.mvp.presenters.IFormResultPresenter;
import pl.rspective.survey.mvp.views.IFormResultView;
import pl.rspective.survey.ui.AbsActivity;
import pl.rspective.survey.ui.form.MasterFormFragment;
import pl.rspective.survey.ui.form.adapter.FormQuestionPagerAdapter;


public class ResultFragment extends Fragment implements IFormResultView {

    @Inject
    Bus bus;

    @Inject
    LocalPreferences localPreferences;

    @Inject
    IFormResultPresenter formPresenter;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.viewpagertab)
    SmartTabLayout smartTabLayout;

    @InjectView(R.id.barchart)
    PieChart mChart;

    private FormQuestionPagerAdapter adapter;

    private Survey surveyResult;

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

        adapter = new FormQuestionPagerAdapter(getFragmentManager(), null);
        smartTabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateBarChart(position);
            }
        });

        formPresenter.loadSurveyResults();
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

        PieData data = new PieData(labels, ds);
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
        if (survey == null || survey.getQuestions() == null) {
            return;
        }

        this.surveyResult = survey;

        updateBarChart(0);

        adapter.updateData(survey.getQuestions());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void showResultFragment() {
        ((AbsActivity) getActivity()).addFragment(ResultFragment.newInstance(), false, R.id.fl_main_fragment_container);
    }

    @Override
    public void showFormFragment() {
        ((AbsActivity) getActivity()).addFragment(MasterFormFragment.newInstance(), false, R.id.fl_main_fragment_container);
    }


    @Subscribe
    public void onSurveyResultsUpdateEvent(SurveyResultsUpdateEvent updateEvent) {
        formPresenter.loadSurveyResults();
    }

    @Subscribe
    public void onSurveyResetEvent(SurveyRestartEvent surveyRestartEvent) {
        localPreferences.setAppEventStatus(AppEventStatus.NO_EVENTS.ordinal());

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Nowa ankieta!")
                .setContentText("Nastąpi ponowne załadowanie ankiety")
                .setConfirmText("Ok")
                .setCustomImage(R.drawable.ic_synchronisation)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        formPresenter.restartSurvey();
                    }
                })
                .show();

    }
}