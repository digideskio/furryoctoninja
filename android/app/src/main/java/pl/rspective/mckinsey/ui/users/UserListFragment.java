package pl.rspective.mckinsey.ui.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.mckinsey.R;

public class UserListFragment extends Fragment {

    private static final String HAS_USERS_FILLEDOUT_THE_FORM_EXTRA = "has_users_filledout_form_extra";

    @InjectView(R.id.list_survey_users)
    RecyclerView listSurveyUsers;

    public static UserListFragment newInstance(boolean hasFilledoutTheForm) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(HAS_USERS_FILLEDOUT_THE_FORM_EXTRA, hasFilledoutTheForm);

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_users_list, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listSurveyUsers.setLayoutManager(llm);
    }
}
