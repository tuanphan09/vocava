package com.bit.vocava.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit.vocava.R;
import com.bit.vocava.models.SentenceTest;

/**
 * A simple {@link Fragment} subclass.
 */
public class SentenceTestFragment extends BaseFragment {


  private SentenceTest sentenceTest;

  public static SentenceTestFragment newInstance(SentenceTest test) {
    SentenceTestFragment fragments = new SentenceTestFragment();
    fragments.sentenceTest = test;
    return  fragments;
  }

  public SentenceTestFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_sentence_test, container, false);
  }

}
