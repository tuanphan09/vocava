package com.bit.vocava.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.vocava.R;
import com.bit.vocava.adapters.LearningWordAdapter;
import com.bit.vocava.api.UserInfoApi;
import com.bit.vocava.models.MyUserInfo;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearningWordFragment extends BaseFragment {

  @BindView(R.id.rcv)
  RecyclerView recyclerView;
  LearningWordAdapter adapter;

  @BindView(R.id.progress_layout)
  View layoutProgress;

  public LearningWordFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_learning_word, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Glide.with(this)
            .load(R.drawable.ic_loading)
            .into((ImageView) view.findViewById(R.id.img_progress));

    layoutProgress.setVisibility(View.VISIBLE);
    new Thread(() -> {
      MyUserInfo myUserInfo = UserInfoApi.getUserInfo();
      layoutProgress.setVisibility(View.INVISIBLE);
      if (myUserInfo == null) return;
      getActivity().runOnUiThread(() -> {
        adapter = new LearningWordAdapter(getContext(), myUserInfo.getList(), view1 -> {
          InfoLearningWordFragment.getInstance(getContext(), getActivity())
                  .setWord(((TextView) view1).getText().toString())
                  .setView(R.layout.learning_word_info)
                  .show();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
      });
    }).start();
  }
}
