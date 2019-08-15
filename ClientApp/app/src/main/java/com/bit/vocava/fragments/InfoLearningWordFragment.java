package com.bit.vocava.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.service.autofill.VisibilitySetterAction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.bit.vocava.R;
import com.bit.vocava.api.VocabularyApi;
import com.bit.vocava.models.Vocabulary;

import java.io.IOException;
import java.text.BreakIterator;

public class InfoLearningWordFragment {

  private static InfoLearningWordFragment instance;
  private ResultDialogFragment.OnClickOkListener listener;
  private Dialog dialog;
  private Context mContext;
  private ProgressBar progressBar;
  private TextView tvWord;
  private View layoutDefinition;
  private Thread thread;
  private Activity activity;
  private String word;
  private TextView tvDefinition;
  private TextView tvPronun;
  private View btnAudio;
  private TextView tvExample;

  public static InfoLearningWordFragment getInstance(Context context, Activity activity) {
    if (instance == null) {
      instance = new InfoLearningWordFragment();
      instance.dialog = new Dialog(context);
      instance.mContext = context;
      instance.activity = activity;
    }
    return instance;
  }

  public InfoLearningWordFragment setView(@LayoutRes int resID) {
    dialog.setContentView(resID);

    progressBar = dialog.findViewById(R.id.progress_bar);
    tvWord = dialog.findViewById(R.id.tv_word);
    layoutDefinition = dialog.findViewById(R.id.layout_definition);
    tvDefinition = dialog.findViewById(R.id.tv_definition);
    tvPronun = dialog.findViewById(R.id.tv_pronounce);
    btnAudio = dialog.findViewById(R.id.btn_audio);
    tvExample = dialog.findViewById(R.id.tv_example);

    dialog.setOnDismissListener(dialogInterface -> {
      Thread.currentThread().interrupt();
    });
    tvWord.setText(word);
    layoutDefinition.setVisibility(View.INVISIBLE);
    progressBar.setVisibility(View.VISIBLE);
    new Thread(() -> {
      Vocabulary tmp = VocabularyApi.getVocabulary(word);
      activity.runOnUiThread(() -> {
        if (tmp == null) {
          layoutDefinition.setVisibility(View.VISIBLE);
          progressBar.setVisibility(View.GONE);
          return;
        }
        if (tmp.getShortDefinitions() != null && tmp.getShortDefinitions().length != 0)
          tvDefinition.setText(tmp.getShortDefinitions()[0]);
        else if (tmp.getDefinitions() != null && tmp.getDefinitions().length != 0)
          tvDefinition.setText(tmp.getDefinitions()[0]);
        tvPronun.setText(tmp.getSpelling());
        tvExample.setText(tmp.getExample());
        btnAudio.setOnClickListener(view2 -> {
          new Thread(() -> {
            try {
              MediaPlayer mediaPlayer = new MediaPlayer();
              mediaPlayer.setDataSource(tmp.getAudioFile());
              mediaPlayer.prepare();
              mediaPlayer.setOnCompletionListener(MediaPlayer::release);
              mediaPlayer.start();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }).start();
        });
        layoutDefinition.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
      });
    }).start();
    return this;
  }

  public void show() {
    dialog.show();
  }

  public InfoLearningWordFragment setWord(String word) {
    this.word = word;
    return this;
  }

  public interface OnClickOkListener {
    void onOk();
  }
}
