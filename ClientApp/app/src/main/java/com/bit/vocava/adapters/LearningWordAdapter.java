package com.bit.vocava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;
import com.bit.vocava.models.LearningWord;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.List;

public class LearningWordAdapter extends RecyclerView.Adapter<LearningWordAdapter.Holder> {

  private Context mContext;
  private List<LearningWord> list;
  private View.OnClickListener listener;

  public LearningWordAdapter(Context mContext, List<LearningWord> list, View.OnClickListener listener) {
    this.mContext = mContext;
    this.list = list;
    this.listener = listener;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_learning_word, parent, false);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    LearningWord learningWord = list.get(position);
    holder.textView.setText(learningWord.getWord());
    holder.progressBar.setProgress(learningWord.getScore());
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class Holder extends RecyclerView.ViewHolder {

    RoundedHorizontalProgressBar progressBar;
    TextView textView;

    public Holder(@NonNull View itemView) {
      super(itemView);

      progressBar = itemView.findViewById(R.id.progress_bar);
      textView = itemView.findViewById(R.id.tv_word);
      itemView.setOnClickListener(view -> listener.onClick(textView));
    }
  }
}
