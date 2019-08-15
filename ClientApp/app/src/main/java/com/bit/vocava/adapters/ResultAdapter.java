package com.bit.vocava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;
import com.bit.vocava.models.ResultInfo;
import com.bit.vocava.models.WordInfo;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {

  private Context mContext;
  private List<WordInfo> resultInfos;

  public ResultAdapter(Context mContext, List<WordInfo> resultInfos) {
    this.mContext = mContext;
    this.resultInfos = resultInfos;
  }

  @NonNull
  @Override
  public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_result_2, parent, false);
    return new ResultHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
    holder.tvWord.setText(resultInfos.get(position).getWord());
    if (resultInfos.get(position).getScoreIncrease() > 0) {
      holder.tvScore.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.green, null));
      holder.tvScore.setText("+" + resultInfos.get(position).getScoreIncrease());
    } else {
      holder.tvScore.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.fbutton_color_pomegranate, null));
      holder.tvScore.setText("" + resultInfos.get(position).getScoreIncrease());
    }
    holder.progressBar.animateProgress( 100, resultInfos.get(position).getNewScore());
  }

  @Override
  public int getItemCount() {
    return resultInfos.size();
  }

  class ResultHolder extends RecyclerView.ViewHolder {

    RoundedHorizontalProgressBar progressBar;
    TextView tvWord;
    TextView tvScore;

    public ResultHolder(@NonNull View itemView) {

      super(itemView);
      tvScore = itemView.findViewById(R.id.tv_score);
      tvWord = itemView.findViewById(R.id.tv_word);
      progressBar = itemView.findViewById(R.id.progress_bar);
    }
  }
}
