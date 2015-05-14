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

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.Question;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.form.adapter.AnswerAdapter;

public class FormQuestionFragment extends Fragment {

    private static final String QUESTION_EXTRA = "question_extra";

    @InjectView(R.id.tv_form_question_title)
    TextView tvFormQuestionTitle;

    @InjectView(R.id.list_survey_answers)
    RecyclerView recyclerViewAnswers;

    private Question question;
    private AnswerAdapter adapter;

    public static FormQuestionFragment newInstance(Question question) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION_EXTRA, question);

        FormQuestionFragment fragment = new FormQuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        question = (Question) getArguments().getSerializable(QUESTION_EXTRA);
        adapter = new AnswerAdapter();
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
}
