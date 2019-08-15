package com.bit.vocava.fragments;


import android.app.MediaRouteButton;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.vocava.R;
import com.bit.vocava.api.UserInfoApi;
import com.bit.vocava.callback.BasicProgressListener;
import com.bit.vocava.models.MyUserInfo;
import com.bit.vocava.models.TestVocavaManager;
import com.bumptech.glide.Glide;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends TestFragment {


  @BindView(R.id.progress_layout)
  View layoutProgress;
  @BindView(R.id.progress_level)
  ColorfulRingProgressView progressLevel;
  @BindView(R.id.tv_level)
  TextView tvLevel;
  private MyUserInfo myUserInfo;

  public ExamFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_exam, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    updateLevelView();
    Glide.with(this)
            .load(R.drawable.ic_loading)
            .into((ImageView) view.findViewById(R.id.img_progress));
  }

  @OnClick(R.id.btn_learn_voca)
  void onClickLearnVoca() {
    TestVocavaManager.getInstance().initTest(new BasicProgressListener() {
      @Override
      public void onStart() {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            layoutProgress.setVisibility(View.VISIBLE);
          }
        });
      }

      @Override
      public void onProgress(float progress) {

      }

      @Override
      public void onCompleted() {
        layoutProgress.setVisibility(View.INVISIBLE);
        addFragmentWithTestQuestion(TestVocavaManager.getInstance().getCurrentQuestion());
      }
    });
  }

  public void updateLevelView() {
    new Thread(() -> {
      myUserInfo = UserInfoApi.getUserInfo();
      if (myUserInfo == null) return;
      getActivity().runOnUiThread(() -> {
        tvLevel.setText("Level " + myUserInfo.getLevel());
        progressLevel.setPercent(myUserInfo.getLevel() * 100f / 10f);
      });
    }).start();
  }

  @OnClick(R.id.progress_layout)
  void onClickLProgressLayout() {

  }
}
