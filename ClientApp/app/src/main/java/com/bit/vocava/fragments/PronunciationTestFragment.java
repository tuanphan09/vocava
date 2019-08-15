package com.bit.vocava.fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;
import com.bit.vocava.adapters.MultipleChoiceAdapter;
import com.bit.vocava.models.MyUserManager;
import com.bit.vocava.models.PronunciationTest;
import com.bit.vocava.models.TestVocavaManager;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PronunciationTestFragment extends TestFragment {

  @BindView(R.id.rcv_option)
  RecyclerView rcvOptions;
  MultipleChoiceAdapter adapter;

  @BindView(R.id.progress_bar)
  RoundedHorizontalProgressBar progressBar;

  private PronunciationTest pronunciationTest;

  public static PronunciationTestFragment newInstance(PronunciationTest pronunciationTest) {

    PronunciationTestFragment fragment = new PronunciationTestFragment();
    fragment.pronunciationTest = pronunciationTest;
    return fragment;
  }

  public PronunciationTestFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_pronunciation_test, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    progressBar.setMax(TestVocavaManager.getInstance().getNumberQuestion());
    progressBar.setProgress(TestVocavaManager.getInstance().getCurrentIndexQuestion());

    adapter = new MultipleChoiceAdapter(getContext(), pronunciationTest.getOptions());
    rcvOptions.setAdapter(adapter);
    rcvOptions.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
  }

  @OnClick(R.id.btn_question)
  void onClickQuestion() {
    new Thread(() -> {
      MediaPlayer mp = new MediaPlayer();
      try {
        mp.setDataSource(pronunciationTest.getQuestion());
        mp.prepare();
        mp.setOnCompletionListener(mediaPlayer -> mp.release());
        mp.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @OnClick(R.id.btn_check)
  void onClickCheck() {

    int resID;
    if (adapter.getCurrentIndex() == (int) pronunciationTest.getAnswer()) {
      TestVocavaManager.getInstance().onHaveCorrectAnswer();
      resID = R.layout.dialog_result_good;
    } else  {
      TestVocavaManager.getInstance().onHaveWrongAnswer();
      resID = R.layout.dialog_result_wrong;
    }
    ResultDialogFragment.getInstance(getContext()).setView(resID).setOnOkListener(() -> {
      addFragmentWithTestQuestion(TestVocavaManager.getInstance().nextQuestion());
    }).show();
  }

  @OnClick(R.id.btn_close)
  void onClose() {
    onBackPressed();
  }
}
