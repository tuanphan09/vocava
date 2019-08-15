package com.bit.vocava.fragments;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;

import com.bit.vocava.R;


public class ResultDialogFragment  {

  private static ResultDialogFragment instance;
  private OnClickOkListener listener;
  private Dialog dialog;
  private Context mContext;

  public static ResultDialogFragment getInstance(Context context) {
    if (instance == null) {
      instance = new ResultDialogFragment();
      instance.dialog = new Dialog(context);
      instance.mContext = context;
    }
    return instance;
  }

  public ResultDialogFragment setView(@LayoutRes int resID) {
    dialog.setContentView(resID);
    Button button = dialog.findViewById(R.id.btn_next);
    button.setOnClickListener(view -> {
      Log.d("AppLog", "OK Button");
      dialog.dismiss();
      if (listener != null) listener.onOk();
    });
    dialog.setCancelable(false);
    return this;
  }

  public void show() {
    dialog.show();
  }

  public ResultDialogFragment setOnOkListener(OnClickOkListener listener) {
    this.listener = listener;
    return this;
  }

  public interface OnClickOkListener {
    void onOk();
  }
}
