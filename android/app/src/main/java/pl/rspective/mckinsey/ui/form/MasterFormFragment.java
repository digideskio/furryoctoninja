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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pl.rspective.data.entity.Answer;
import pl.rspective.data.entity.Question;
import pl.rspective.data.entity.Survey;
import pl.rspective.data.local.LocalPreferences;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.architecture.bus.events.SurveyChangedEvent;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.data.model.AppEventStatus;
import pl.rspective.mckinsey.data.model.SurveySubmitResultType;
import pl.rspective.mckinsey.mvp.presenters.IFormPresenter;
import pl.rspective.mckinsey.mvp.views.IFormView;
import pl.rspective.mckinsey.ui.AbsActivity;
import pl.rspective.mckinsey.ui.form.adapter.FormQuestionPagerAdapter;
import pl.rspective.mckinsey.ui.results.ResultFragment;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class MasterFormFragment extends Fragment implements IFormView, FormQuestionFragment.QuestionListener {

    @Inject
    Bus bus;

    @Inject
    LocalPreferences localPreferences;

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
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
        formPresenter.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.inject(this, view);
        typeface(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FormQuestionPagerAdapter(getFragmentManager(), this);
        formPresenter.loadSurvey();
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

        adapter.updateData(survey.getQuestions());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public void showSubmitButton() {
        if (btnSurveySubmit.getVisibility() == View.VISIBLE) {
            return;
        }

        btnSurveySubmit.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceInUp)
                .duration(200)
                .playOn(btnSurveySubmit);
    }

    @Override
    public void showResultFragment() {
        ((AbsActivity) getActivity()).addFragment(ResultFragment.newInstance(), false, R.id.fl_main_fragment_container);
    }

    @Override
    public void showSubmitDialog(SurveySubmitResultType resultType) {
        SweetAlertDialog dialog;
        switch (resultType) {
            case SURVEY_OK:
                dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("")
                        .setContentText(getString(resultType.getLabelTxtId()))
                        .setConfirmText(getString(resultType.getButtonTxtId()))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                showResultFragment();
                            }
                        });
                dialog.setCancelable(false);
                dialog.show();
                break;
            case SURVEY_ALREADY_SEND:
                dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Uwaga")
                        .setContentText(getString(resultType.getLabelTxtId()))
                        .setConfirmText(getString(resultType.getButtonTxtId()))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                showResultFragment();
                                sDialog.dismissWithAnimation();
                            }
                        });
                dialog.show();
                break;
            case SURVEY_WAS_CHANGED:
                dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Uwaga")
                        .setContentText(getString(resultType.getLabelTxtId()))
                        .setConfirmText(getString(resultType.getButtonTxtId()))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                formPresenter.resetSurvey();
                                sDialog.dismissWithAnimation();
                            }
                        });
                dialog.show();
                break;
            case SURVEY_ERROR:
                dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Uwaga")
                        .setContentText(getString(resultType.getLabelTxtId()))
                        .setConfirmText(getString(resultType.getButtonTxtId()))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                btnSurveySubmit.setEnabled(true);
                                sDialog.dismissWithAnimation();
                            }
                        });
                dialog.show();
                break;

        }
    }

    @Override
    public void nextQuestion(int position) {
        if (position < adapter.getCount() - 1) {
            viewPager.setCurrentItem(position + 1);
        }
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void onQuestionUpdate(int number, Question question, Answer answer) {
        formPresenter.updateSurvey(number, question, answer);
    }

    @OnClick(R.id.btn_survey_submit)
    public void onSurveySubmitClick() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Potwierdzenie")
                .setContentText("Czy na pewno chcesz wysłać ankietę?")
                .setConfirmText("Wyślij")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        btnSurveySubmit.setEnabled(false);
                        sDialog.dismissWithAnimation();
                        formPresenter.submitSurvey();
                    }
                })
                .show();
    }

    @Subscribe
    public void onSurveyChangedEvent(SurveyChangedEvent changedEvent) {
        localPreferences.setAppEventStatus(AppEventStatus.NO_EVENTS.ordinal());

        formPresenter.resetSurvey();
        formPresenter.loadSurvey();

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Ankieta aktualizacja")
                .setContentText("Nastąpiła aktualizacja ankiety")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

}
