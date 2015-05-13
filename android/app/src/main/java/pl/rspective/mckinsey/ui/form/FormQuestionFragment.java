package pl.rspective.mckinsey.ui.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.mckinsey.R;

public class FormQuestionFragment extends Fragment {

    @InjectView(R.id.tv_form_question_title)
    TextView tvFormQuestionTitle;

    public static FormQuestionFragment newInstance() {
        return new FormQuestionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_question, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

}
