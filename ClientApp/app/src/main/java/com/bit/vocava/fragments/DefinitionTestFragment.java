package com.bit.vocava.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;
import com.bit.vocava.adapters.MultipleChoiceAdapter;
import com.bit.vocava.models.DefinitionTest;
import com.bit.vocava.models.MyUserManager;
import com.bit.vocava.models.TestVocavaManager;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DefinitionTestFragment extends TestFragment {

  @BindView(R.id.rcv_option)
  RecyclerView rcvOptions;
  MultipleChoiceAdapter adapter;

  @BindView(R.id.progress_bar)
  RoundedHorizontalProgressBar progressBar;
  @BindView(R.id.tv_question)
  TextView tvQuestion;

  private DefinitionTest definitionTest;

  public static DefinitionTestFragment newInstance(DefinitionTest definitionTest) {
    DefinitionTestFragment fragment = new DefinitionTestFragment();
    fragment.definitionTest = definitionTest;
    return fragment;
  }

  public DefinitionTestFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_definition_test, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    tvQuestion.setText(definitionTest.getQuestion() + "?");

    progressBar.setMax(TestVocavaManager.getInstance().getNumberQuestion());
    progressBar.setProgress(TestVocavaManager.getInstance().getCurrentIndexQuestion());

    adapter = new MultipleChoiceAdapter(getContext(), definitionTest.getOptions());
    rcvOptions.setAdapter(adapter);
    rcvOptions.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
  }

  @OnClick(R.id.btn_check)
  void onClickCheck() {

    int resID;
    if (adapter.getCurrentIndex() == (int) definitionTest.getAnswer()) {
      TestVocavaManager.getInstance().onHaveCorrectAnswer();
      resID = R.layout.dialog_result_good;
      Log.d("AppLog", definitionTest.getWord() + " Dung");
    } else  {
      TestVocavaManager.getInstance().onHaveWrongAnswer();
      resID = R.layout.dialog_result_wrong;
      Log.d("AppLog", definitionTest.getWord() + " Sai");
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
