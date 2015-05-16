package pl.rspective.mckinsey.ui.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.Answer;
import pl.rspective.data.entity.Question;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.architecture.bus.events.AnswerUpdateEvent;
import pl.rspective.mckinsey.dagger.Injector;
import pl.rspective.mckinsey.ui.form.adapter.AnswerAdapter;

public class FormQuestionFragment extends Fragment implements AnswerAdapter.AnswerListener {

    public static interface QuestionListener {
        void onQuestionUpdate(int number, Question question);
    }

    private static final String QUESTION_EXTRA = "question_extra";
    private static final String QUESTION_NUMBER_EXTRA = "question_number_extra";

    @Inject
    Bus bus;

    @InjectView(R.id.tv_form_question_title)
    TextView tvFormQuestionTitle;

    @InjectView(R.id.list_survey_answers)
    RecyclerView recyclerViewAnswers;

    private int questionNumber;
    private Question question;
    private AnswerAdapter adapter;

    private QuestionListener questionListener;

    public static FormQuestionFragment newInstance(int number, Question question) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION_EXTRA, question);
        bundle.putInt(QUESTION_NUMBER_EXTRA, number);

        FormQuestionFragment fragment = new FormQuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        question = (Question) getArguments().getSerializable(QUESTION_EXTRA);
        questionNumber = getArguments().getInt(QUESTION_NUMBER_EXTRA);

        adapter = new AnswerAdapter(this);
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
        View view = inflater.inflate(R.layout.fragment_form_answers, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.updateData(question.getAnswers());
        tvFormQuestionTitle.setText(question.getText());

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAnswers.setLayoutManager(llm);
        recyclerViewAnswers.setAdapter(adapter);
    }

    @Override
    public void onAnswerClick(int position, Answer answer) {
        question.setUserAnswerId(answer.getId());

        for(Answer a : question.getAnswers()) {
            if(a.getId() == answer.getId()) {
                a.setSelected(true);
            } else {
                a.setSelected(false);
            }
        }

        questionListener.onQuestionUpdate(questionNumber, question);
    }

    public void setQuestionListener(QuestionListener questionListener) {
        this.questionListener = questionListener;
    }

    @Subscribe
    public void onAnswerUpdateEvent(AnswerUpdateEvent answerUpdateEvent) {
        if(question.getId() == answerUpdateEvent.getQuestion().getId()) {
            adapter.updateData(answerUpdateEvent.getQuestion().getAnswers());
        }
    }

}
