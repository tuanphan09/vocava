package com.bit.vocava.fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.bit.vocava.R;
import com.bit.vocava.adapters.PuzzleAdapter;
import com.bit.vocava.models.PuzzleTest;
import com.bit.vocava.models.TestVocavaManager;
import com.library.flowlayout.FlowLayoutManager;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PuzzleTestFragment extends TestFragment {

  @BindView(R.id.rcv_option)
  RecyclerView rcvOptions;
  private PuzzleAdapter adapter;
  @BindView(R.id.progress_bar)
  RoundedHorizontalProgressBar progressBar;
  @BindView(R.id.rcv_answer)
  RecyclerView rcvAnswer;
  private PuzzleAdapter adapterAnswer;

  private PuzzleTest puzzleTest;

  public static PuzzleTestFragment newInstance(PuzzleTest puzzleTest) {
    PuzzleTestFragment fragment = new PuzzleTestFragment();
    fragment.puzzleTest = puzzleTest;
    return fragment;
  }

  public PuzzleTestFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_puzzle, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    progressBar.setMax(TestVocavaManager.getInstance().getNumberQuestion());
    progressBar.setProgress(TestVocavaManager.getInstance().getCurrentIndexQuestion());

    RecyclerView.LayoutManager l1 = new LinearLayoutManager(getContext());
    ((LinearLayoutManager) l1).setOrientation(RecyclerView.HORIZONTAL);
    RecyclerView.LayoutManager l2 = new LinearLayoutManager(getContext());
    ((LinearLayoutManager) l2).setOrientation(RecyclerView.HORIZONTAL);
    rcvOptions.setLayoutManager(ChipsLayoutManager.newBuilder(getContext()).build());
    rcvAnswer.setLayoutManager(ChipsLayoutManager.newBuilder(getContext()).build());
    for (int i = 0; i < puzzleTest.getOptions().length; i++) Log.d("AppLog", puzzleTest.getOptions()[i]);
    adapter = new PuzzleAdapter(getContext(), Arrays.asList(puzzleTest.getOptions()), text -> {
      Log.d("AppLog", text);
      adapterAnswer.addWord(text);
    });
    ArrayList<String> tmp = new ArrayList<>();
    for (int i = 0; i < puzzleTest.getOptions().length; i++) tmp.add("");
    adapterAnswer = new PuzzleAdapter(getContext(), tmp, text -> {
      Log.d("AppLog", text);
      adapter.addWord(text);
    });
    rcvOptions.setAdapter(adapter);
    rcvAnswer.setAdapter(adapterAnswer);
  }

  @OnClick(R.id.btn_question)
  void onClickQuestion() {
    MediaPlayer mp = new MediaPlayer();
    try {
      mp.setDataSource(puzzleTest.getQuestion());
      mp.prepare();
      mp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @OnClick(R.id.btn_check)
  void onClickCheck() {

    Log.d("AppLog", adapterAnswer.getWord() + " " + puzzleTest.getAnswer());
    int resID;
    if (adapterAnswer.getWord().equals(puzzleTest.getAnswer())) {
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
