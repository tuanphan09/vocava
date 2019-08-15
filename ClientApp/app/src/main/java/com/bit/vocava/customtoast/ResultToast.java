package com.bit.vocava.customtoast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bit.vocava.R;

public class ResultToast {

  public static void show(Context context, boolean isCorrect) {
    Toast toast = new Toast(context);
    toast.setDuration(Toast.LENGTH_SHORT);
    View layout;
    if (isCorrect) layout = LayoutInflater.from(context).inflate(R.layout.result_toast_correct, null, false);
    else layout = LayoutInflater.from(context).inflate(R.layout.result_toast_incorrect, null, false);
    toast.setView(layout);
    toast.show();
  }
}
