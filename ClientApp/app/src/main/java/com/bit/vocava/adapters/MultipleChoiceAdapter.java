package com.bit.vocava.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;

import info.hoang8f.widget.FButton;

public class MultipleChoiceAdapter extends RecyclerView.Adapter<MultipleChoiceAdapter.OptionsHolder> {

  private Context mContext;
  private String[] options;
  private int currentIndex = -1;

  public MultipleChoiceAdapter(Context mContext, String[] options) {
    this.mContext = mContext;
    this.options = options;
  }

  @NonNull
  @Override
  public OptionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_option, parent, false);
    return new OptionsHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull OptionsHolder holder, int position) {
    holder.button.setText(options[position]);
    if (currentIndex == position) {
      holder.button.setButtonColor(ContextCompat.getColor(mContext, R.color.fbutton_default_color));
      holder.button.setTextColor(Color.WHITE);
    } else {
      holder.button.setButtonColor(ContextCompat.getColor(mContext, R.color.unselectedColor));
      holder.button.setTextColor(Color.BLACK);
    }
  }

  @Override
  public int getItemCount() {
    return options.length;
  }

  class OptionsHolder extends RecyclerView.ViewHolder {

    FButton button;

    public OptionsHolder(@NonNull View itemView) {
      super(itemView);

      button = itemView.findViewById(R.id.option);
      button.setOnClickListener(view -> {
        currentIndex = getAdapterPosition();
        notifyDataSetChanged();
      });
    }
  }

  public int getCurrentIndex() {
    return currentIndex;
  }

  public void setCurrentIndex(int currentIndex) {
    this.currentIndex = currentIndex;
  }
}
