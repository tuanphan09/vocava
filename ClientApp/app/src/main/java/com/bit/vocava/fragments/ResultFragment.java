package com.bit.vocava.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;
import com.bit.vocava.adapters.ResultAdapter;
import com.bit.vocava.api.ResultApi;
import com.bit.vocava.models.MyUserManager;
import com.bit.vocava.models.ResultInfo;
import com.bit.vocava.models.TestVocavaManager;
import com.bit.vocava.models.WordInfo;
import com.github.mikephil.charting.charts.PieChart;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends TestFragment {


  @BindView(R.id.rcv_result)
  RecyclerView rcv;
  private ResultAdapter adapter;

  public ResultFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_result, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    rcv.setLayoutManager(new LinearLayoutManager(getContext()));
    new Thread(() -> {
      try {
        String jsonStr = ResultApi.postResult(TestVocavaManager.getInstance().getResultInfos());
        JSONArray jsonArr = new JSONArray(jsonStr);
        List<WordInfo> list = new ArrayList<>();
        for (int i = 0; i < jsonArr.length(); i++) {
          JSONObject jsonObject = jsonArr.getJSONObject(i);
          String word = jsonObject.getString("word");
          int newScore = jsonObject.getInt("new_ma_score");
          int oldScore = jsonObject.getInt("old_ma_score");
          list.add(new WordInfo(word, newScore, oldScore));
        }
        getActivity().runOnUiThread(() -> {
          rcv.setAdapter(new ResultAdapter(getContext(), list));
        });
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @OnClick(R.id.btn_back)
  void onClickBack() {
    onBackPressed();
  }
}
