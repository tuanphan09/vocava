package com.bit.autodetectwordview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class DetectOverlay extends FrameLayout {

  private String word;
  private RectF position;

  public DetectOverlay(Context context, String word, RectF position) {
    super(context);
    this.word = word;
    this.position = position;
  }

  public DetectOverlay(Context context) {
    super(context);
  }

  public DetectOverlay(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public RectF getPosition() {
    return position;
  }

  public void setPosition(RectF position) {
    this.position = position;
  }
}
