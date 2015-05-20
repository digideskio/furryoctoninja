package pl.rspective.mckinsey.ui.form.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.data.entity.Answer;
import pl.rspective.mckinsey.R;
import pl.rspective.mckinsey.ui.common.AdapterListener;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> implements AdapterListener<Answer> {

    public static interface AnswerListener {
        void onAnswerClick(int position, Answer answer);
    }

    private List<Answer> answers;
    private AnswerListener answerListener;

    public AnswerAdapter(AnswerListener answerListener) {
        this.answers = new ArrayList<>();
        this.answerListener = answerListener;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_answer, parent, false);
        return new AnswerViewHolder(this, itemView);
    }

    @Override
    public void onBindViewHolder(final AnswerViewHolder holder, int position) {
        Answer answer = answers.get(position);

        holder.tvAnswer.setText(answer.getText());
        holder.tvAnswerIndex.setText("abcdefgh".charAt(position) + ")");

        if(answer.isSelected()) {
            holder.ivAnswerCheck.setVisibility(View.VISIBLE);

            YoYo.with(Techniques.ZoomIn)
                    .duration(250)
                    .playOn(holder.ivAnswerCheck);
        } else {
            YoYo.with(Techniques.FadeOut)
                    .duration(250)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.ivAnswerCheck.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    })
                    .playOn(holder.ivAnswerCheck);
        }
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
        answerListener.onAnswerClick(position, answers.get(position));
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_question_answer)
        TextView tvAnswer;

        @InjectView(R.id.tv_question_answer_index)
        TextView tvAnswerIndex;

        @InjectView(R.id.iv_answer_check)
        ImageView ivAnswerCheck;

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
