package com.bit.vocava.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bit.vocava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeansTestFragment extends TestFragment {


  public MeansTestFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_means_test, container, false);
  }

}
