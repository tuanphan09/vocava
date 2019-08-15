package com.bit.vocava.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.TextHolder> {

  private Context mContext;
  private List<String> options;
  private String wordAdd = "";
  private OnClickItem onClickItem;

  public PuzzleAdapter(Context mContext, List<String> options, OnClickItem onClickItem) {
    this.mContext = mContext;
    this.options = options;
    this.onClickItem = onClickItem;
  }

  @NonNull
  @Override
  public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_puzzle, parent, false);
    return new TextHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TextHolder holder, int position) {
    Log.d("AppLog", "On Bind");
    if (options.get(position) == null)  options.set(position, "");
    if (options.get(position).equals("")) {
      holder.puzzle.setVisibility(View.INVISIBLE);
    } else {
      holder.puzzle.setVisibility(View.VISIBLE);
    }
    holder.puzzle.setText(options.get(position));
  }

  @Override
  public int getItemCount() {
    return options.size();
  }

  public void addWord(String text) {
    options = new ArrayList<>(options);
    for (int i = 0; i < options.size(); i++) {
      if (options.get(i).equals("")) {
        options.set(i, text);
        notifyDataSetChanged();
        break;
      }
    }
  }

  class TextHolder extends RecyclerView.ViewHolder {

    FButton puzzle;

    public TextHolder(@NonNull View itemView) {
      super(itemView);

      puzzle = itemView.findViewById(R.id.puzzle);
      puzzle.setOnClickListener(view -> {
        puzzle.setVisibility(View.INVISIBLE);
        String text = options.get(getAdapterPosition());
        onClickItem.onClickItem(text);
        options.set(getAdapterPosition(), "") ;
        notifyItemChanged(getAdapterPosition());
      });
    }
  }

  public String getWord() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < options.size(); i++) builder.append(options.get(i));
    return builder.toString();
  }

  public interface OnClickItem {
    void onClickItem(String text);
  }
}
