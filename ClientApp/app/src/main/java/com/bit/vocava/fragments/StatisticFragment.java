package com.bit.vocava.fragments;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit.vocava.R;
import com.bit.vocava.api.StatisticApi;
import com.bit.vocava.api.UserInfoApi;
import com.bit.vocava.models.MyUserInfo;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends BaseFragment {


  @BindView(R.id.pie_chart)
  PieChart pieChart;
  private JSONObject jsonObj;
  @BindView(R.id.lineChar)
  LineChart lineChart;

  public StatisticFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_statistic, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    //set up pie char
    pieChart.setUsePercentValues(true);
    pieChart.getDescription().setEnabled(false);
    pieChart.setExtraOffsets(5, 10, 5, 5);

    pieChart.setDragDecelerationFrictionCoef(0.95f);

    pieChart.setDrawHoleEnabled(true);
    pieChart.setHoleColor(Color.WHITE);

    pieChart.setTransparentCircleColor(Color.WHITE);
    pieChart.setTransparentCircleAlpha(110);

    pieChart.setHoleRadius(58f);
    pieChart.setTransparentCircleRadius(61f);

    pieChart.setDrawCenterText(true);

    pieChart.setRotationAngle(0);
    // enable rotation of the pieChart by touch
    pieChart.setRotationEnabled(true);
    pieChart.setHighlightPerTapEnabled(true);

    pieChart.animateY(1400, Easing.EaseInOutQuad);

    Legend l = pieChart.getLegend();
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    l.setOrientation(Legend.LegendOrientation.VERTICAL);
    l.setDrawInside(false);
    l.setXEntrySpace(7f);
    l.setYEntrySpace(0f);
    l.setYOffset(0f);


    //set up line char
    {   // // Chart Style // //

      // background color
      lineChart.setBackgroundColor(Color.WHITE);

      // disable description text
      lineChart.getDescription().setEnabled(false);

      // enable touch gestures
      lineChart.setTouchEnabled(true);

      // set listeners
      lineChart.setDrawGridBackground(false);

      // create marker to display box when values are selected

      // enable scaling and dragging
      lineChart.setDragEnabled(true);
      lineChart.setScaleEnabled(true);
      // lineChart.setScaleXEnabled(true);
      // lineChart.setScaleYEnabled(true);

      // force pinch zoom along both axis
      lineChart.setPinchZoom(true);

      setData();

      // draw points over time
      lineChart.animateX(1500);

      // get the legend (only possible after setting data)
      Legend l1= lineChart.getLegend();

      // draw legend entries as lines
      l1.setForm(Legend.LegendForm.LINE);
    }

    new Thread(() -> {
      jsonObj = StatisticApi.getStatistic();
      if (jsonObj != null) {
        try {
          JSONObject quantity = jsonObj.getJSONObject("quantity");
          ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//          entries.add(new PieEntry((float)quantity.getInt("1"), "Level 1"));
//          entries.add(new PieEntry((float)quantity.getInt("2"), "Level 2"));
//          entries.add(new PieEntry((float)quantity.getInt("3"), "Level 3"));
//          entries.add(new PieEntry((float)quantity.getInt("4"), "Level 4"));

          entries.add(new PieEntry(25f, "Level 1"));
          entries.add(new PieEntry(25f, "Level 2"));
          entries.add(new PieEntry(25f, "Level 3"));
          entries.add(new PieEntry(25f, "Level 4"));

          PieDataSet dataSet = new PieDataSet(entries, "Level word");
          dataSet.setDrawIcons(false);
          dataSet.setSliceSpace(3f);
          dataSet.setIconsOffset(new MPPointF(0, 40));
          dataSet.setSelectionShift(5f);
          PieData data = new PieData(dataSet);
          data.setValueFormatter(new PercentFormatter(pieChart));
          data.setValueTextSize(11f);
          data.setValueTextColor(Color.BLACK);


          ArrayList<Integer> colors = new ArrayList<>();

          for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

          for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

          for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

          for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

          for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

          colors.add(ColorTemplate.getHoloBlue());

          dataSet.setColors(colors);
          getActivity().runOnUiThread(() -> {
            pieChart.setData(data);
            pieChart.highlightValues(null);
            pieChart.invalidate();
          });

        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private void setData() {

    ArrayList<Entry> values = new ArrayList<>();

    new Thread(() -> {

      MyUserInfo myUserInfo = UserInfoApi.getUserInfo();

      for (int i = 0; i < myUserInfo.getScoreExam().length; i++) {
        values.add(new Entry(i, (float) myUserInfo.getScoreExam()[i]));
      }

      LineDataSet set1;

      if (lineChart.getData() != null &&
              lineChart.getData().getDataSetCount() > 0) {
        set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
        set1.setValues(values);
        set1.notifyDataSetChanged();
        lineChart.getData().notifyDataChanged();
        lineChart.notifyDataSetChanged();
      } else {
        // create a dataset and give it a type
        set1 = new LineDataSet(values, "Score Exam");

        set1.setDrawIcons(false);

        // draw dashed line
        set1.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);

        // customize legend entry
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        // text size of values
        set1.setValueTextSize(9f);

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f);

        // set the filled area
        set1.setDrawFilled(true);
        set1.setFillFormatter(new IFillFormatter() {
          @Override
          public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
            return lineChart.getAxisLeft().getAxisMinimum();
          }
        });

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
          // drawables only supported on api level 18 and above
          Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
          set1.setFillDrawable(drawable);
        } else {
          set1.setFillColor(Color.RED);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // set data
        lineChart.setData(data);
      }
    }).start();
  }
}
