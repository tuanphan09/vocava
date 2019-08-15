package com.bit.vocava.fragments;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.bit.autodetectwordview.AutoDetectWordView;
import com.bit.autodetectwordview.OnDetectWordListener;
import com.bit.autodetectwordview.WordDetected;
import com.bit.vocava.R;
import com.bit.vocava.api.OrcImageApi;
import com.bit.vocava.api.VocabularyApi;
import com.bit.vocava.models.Vocabulary;
import com.bit.vocava.utils.BitmapUtils;
import com.bit.vocava.utils.ZoomView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetectPhotoFragment extends BaseFragment {

  @BindView(R.id.img_main)
  ImageView imgMain;
  @BindView(R.id.layout_editor)
  View layoutEditor;
  @BindView(R.id.auto_detect)
  AutoDetectWordView autoDetectWordView;
  private String mPathImage;
  private int imageWidth;
  private int imageHeight;
  private Bitmap currentBitmap;
  @BindView(R.id.layout_zoom)
  ZoomView zoomView;
  @BindView(R.id.bottom_sheet)
  NestedScrollView bottomSheet;
  @BindView(R.id.tv_word)
  TextView tvWord;
  private BottomSheetBehavior sheetBehavior;
  @BindView(R.id.progress_layout)
  View layoutProgress;
  @BindView(R.id.tv_definition)
  TextView tvDefinition;
  @BindView(R.id.layout_definition)
  View layoutDefinition;
  private int oriental = -1;
  @BindView(R.id.progress_bar)
  ProgressBar progressBar;
  @BindView(R.id.tv_pronounce)
  TextView tvPronun;
  @BindView(R.id.btn_audio)
  View btnAudio;

  private Thread thread;
  private MediaPlayer mediaPlayer;

  public DetectPhotoFragment() {

  }

  public static DetectPhotoFragment newInstance(String pathImage) {
    DetectPhotoFragment fragment = new DetectPhotoFragment();
    fragment.mPathImage = pathImage;
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_detect_photo, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    tvPronun.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/arial.TTF"));
    thread = new Thread();
    Glide.with(this)
            .load(R.drawable.ic_loading)
            .into((ImageView) view.findViewById(R.id.img_progress));

    currentBitmap = BitmapFactory.decodeFile(mPathImage);

    oriental = BitmapUtils.getOrientalBitmap(mPathImage);

    Glide.with(this).load(mPathImage)
            .into(imgMain);
    sheetBehavior = BottomSheetBehavior.from(bottomSheet);
    sheetBehavior.setPeekHeight(0);
    autoDetectWordView.setActivity(getActivity());
    autoDetectWordView.setAlpha(0.1f);
    layoutEditor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        if (currentBitmap == null) return;
        removeGlobalOnLayoutListener(layoutEditor, this);
        imageWidth = layoutEditor.getWidth();
        imageHeight = layoutEditor.getHeight();
        if (oriental != 6) {
          resizeImageLayoutDisplay(currentBitmap.getHeight(), currentBitmap.getWidth());
          autoDetectWordView.setRatio((float) imageWidth / currentBitmap.getWidth());
        } else {
          resizeImageLayoutDisplay(currentBitmap.getWidth(), currentBitmap.getHeight());
          autoDetectWordView.setRatio((float) imageWidth / currentBitmap.getHeight());
        }
        getActivity().runOnUiThread(() -> layoutProgress.setVisibility(View.VISIBLE));
        new Thread(() -> {
          String json = OrcImageApi.getWordFromImage(mPathImage);
          autoDetectWordView.detectWord(json, new OnDetectWordListener() {
            @Override
            public void onProgress(WordDetected wordDetected) {

            }

            @Override
            public void onCompleted(List<WordDetected> wordDetectedList) {
              getActivity().runOnUiThread(() -> layoutProgress.setVisibility(View.INVISIBLE));
            }
          });
        }).start();
      }
    });
    autoDetectWordView.onClickDetectedWordListener(view1 -> {
      sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      tvWord.setText(view1.getWord());
      layoutDefinition.setVisibility(View.INVISIBLE);
      progressBar.setVisibility(View.VISIBLE);
      if (thread != null) thread.interrupt();
      new Thread(() -> {
        Vocabulary tmp = VocabularyApi.getVocabulary(view1.getWord());
        getActivity().runOnUiThread(() -> {
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
          btnAudio.setOnClickListener(view2 -> {
            new Thread(() -> {
              try {
                mediaPlayer = new MediaPlayer();
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
      });
      sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private void removeGlobalOnLayoutListener(View target, ViewTreeObserver.OnGlobalLayoutListener listener) {
    if (getSdkVersion() < 16) {
      target.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
    } else {
      target.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
  }

  private int getSdkVersion() {
    return Build.VERSION.SDK_INT;
  }

  private void resizeImageLayoutDisplay(int height, int width) {
    int widthh, heightt;
    float screenProportion = (float) imageWidth / imageHeight;
    float videoProportion = (float) width / height;
    if (videoProportion > screenProportion) {
      widthh = imageWidth;
      heightt = (int) (imageWidth / videoProportion);
    } else {
      widthh = (int) (videoProportion * imageHeight);
      heightt = imageHeight;
    }
    layoutEditor.getLayoutParams().width = widthh;
    layoutEditor.getLayoutParams().height = heightt;
    autoDetectWordView.getLayoutParams().width = widthh;
    autoDetectWordView.getLayoutParams().height = heightt;
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

}
