package pl.rspective.mckinsey.ui.form.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import pl.rspective.mckinsey.ui.common.AdapterListener;
import pl.rspective.mckinsey.ui.form.FormFragment;
import pl.rspective.mckinsey.ui.utils.SmartFragmentStatePagerAdapter;

public class FormQuestionPagerAdapter extends SmartFragmentStatePagerAdapter implements AdapterListener<String> {

    private List<String> questions;

    public FormQuestionPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        questions = new ArrayList<>();
    }

    @Override
    public void updateData(List<String> questions) {
        this.questions.clear();
        this.questions.addAll(questions);

        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Fragment getItem(int position) {
        return FormFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+"";
    }

}