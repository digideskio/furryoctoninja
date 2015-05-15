package pl.rspective.mckinsey.ui.form.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import pl.rspective.data.entity.Question;
import pl.rspective.mckinsey.ui.common.AdapterListener;
import pl.rspective.mckinsey.ui.form.FormQuestionFragment;
import pl.rspective.mckinsey.ui.utils.SmartFragmentStatePagerAdapter;

public class FormQuestionPagerAdapter extends SmartFragmentStatePagerAdapter implements AdapterListener<Question> {

    private List<Question> questions;
    private FormQuestionFragment.QuestionListener questionListener;

    public FormQuestionPagerAdapter(FragmentManager fragmentManager, FormQuestionFragment.QuestionListener questionListener) {
        super(fragmentManager);
        this.questionListener = questionListener;
        this.questions = new ArrayList<>();
    }

    @Override
    public void updateData(List<Question> questions) {
        this.questions.clear();
        this.questions.addAll(questions);

        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {}

    @Override
    public int getCount() {
        return questions == null ? 0 : questions.size();
    }

    @Override
    public Fragment getItem(int position) {
        FormQuestionFragment fragment = FormQuestionFragment.newInstance(position, questions.get(position));
        fragment.setQuestionListener(questionListener);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "";
    }

}