package pl.rspective.survey.ui.users.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.User;
import pl.rspective.survey.R;
import pl.rspective.survey.ui.common.AdapterListener;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements AdapterListener<User> {

    private List<User> users;

    public UserAdapter() {
        this.users = new ArrayList<>();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        typeface(itemView);
        return new UserViewHolder(this, itemView);    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvUser.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void updateData(List<User> data) {
        this.users.clear();
        this.users.addAll(data);

        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_user)
        TextView tvUser;

        public UserViewHolder(final AdapterListener listener, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
