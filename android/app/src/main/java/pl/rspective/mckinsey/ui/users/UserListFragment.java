package pl.rspective.mckinsey.ui.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.User;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.users.adapter.UserAdapter;
import rx.Observable;
import rx.functions.Func1;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class UserListFragment extends Fragment implements MasterUserFragment.UserUpdateListListener {

    private static final String HAS_USERS_FILLEDOUT_THE_FORM_EXTRA = "has_users_filledout_form_extra";

    @InjectView(R.id.list_survey_users)
    RecyclerView listSurveyUsers;

    @InjectView(R.id.rl_empty_list)
    RelativeLayout rlEmptyView;

    private UserAdapter userAdapter;
    private boolean hasFilleddout;

    public static UserListFragment newInstance(boolean hasFilledoutTheForm) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(HAS_USERS_FILLEDOUT_THE_FORM_EXTRA, hasFilledoutTheForm);

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasFilleddout = getArguments().getBoolean(HAS_USERS_FILLEDOUT_THE_FORM_EXTRA, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_users_list, container, false);
        ButterKnife.inject(this, view);
        typeface(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userAdapter = new UserAdapter();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listSurveyUsers.setLayoutManager(llm);

        listSurveyUsers.setAdapter(userAdapter);
    }

    @Override
    public void onUserListReceived(List<User> users) {
        List<User> u = Observable.from(users)
                .filter(new Func1<User, Boolean>() {
                    @Override
                    public Boolean call(User user) {
                        return user.isCompleted() == hasFilleddout;
                    }
                }).toList().toBlocking().single();

        userAdapter.updateData(u);

        if(u == null || u.isEmpty()) {
            rlEmptyView.setVisibility(View.VISIBLE);
        } else {
            rlEmptyView.setVisibility(View.GONE);
        }
    }
}
