package pl.rspective.mckinsey.ui.form.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.Answer;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.common.AdapterListener;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> implements AdapterListener<Answer> {

    private List<Answer> answers;

    public AnswerAdapter() {
        answers = new ArrayList<>();
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_question, parent, false);
        return new AnswerViewHolder(this, itemView);
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        Answer answer = answers.get(position);
        holder.tvAnswer.setText(answer.getText());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    @Override
    public void updateData(List<Answer> data) {
        this.answers.clear();
        this.answers.addAll(data);

        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_question_answer)
        TextView tvAnswer;

        public AnswerViewHolder(final AdapterListener<Answer> listener, View itemView) {
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
