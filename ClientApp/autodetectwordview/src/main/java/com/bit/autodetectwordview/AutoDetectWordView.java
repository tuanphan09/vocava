package com.bit.autodetectwordview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutoDetectWordView extends RelativeLayout {

  private Paint paintBitmap;
  private View contentLayout;
  private List<WordDetected> wordDetectedList;
  private OnDetectWordListener mOnDetectWordListener;
  private Context mContext;
  private List<RectF> rectListDetect;
  private Paint paintRect;
  private Bitmap mBitmap;
  private float ratio = 1;
  private OnClickDetectedWordListener listener;

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  private Activity activity;

  public AutoDetectWordView(Context context) {
    super(context);
    this.mContext = context;
    rectListDetect = new ArrayList<>();
    contentLayout = new View(context);
    mOnDetectWordListener = new OnDetectWordListener() {
      @Override
      public void onProgress(WordDetected wordDetected) {
        addDetectOverlayView(wordDetected);
        Log.d("AppLog", "On progress");
      }

      @Override
      public void onCompleted(List<WordDetected> wordDetectedList) {
        for (WordDetected item : wordDetectedList) {
          addDetectOverlayView(item);
        }
      }
    };
    paintRect = new Paint();
    paintRect.setColor(Color.RED);
  }

  public AutoDetectWordView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.mContext = context;
    rectListDetect = new ArrayList<>();
    contentLayout = new View(context);
    mOnDetectWordListener = new OnDetectWordListener() {
      @Override
      public void onProgress(WordDetected wordDetected) {
        addDetectOverlayView(wordDetected);
        Log.d("AppLog", "On progress");
      }

      @Override
      public void onCompleted(List<WordDetected> wordDetectedList) {
        for (WordDetected item : wordDetectedList) {
          addDetectOverlayView(item);
        }
      }
    };
    paintRect = new Paint();
    paintRect.setColor(Color.RED);
    paintRect.setStrokeWidth(5);
    paintRect.setStyle(Paint.Style.STROKE);
    paintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  private void addDetectOverlayView(WordDetected wordDetected) {
    RectF[] rectList = wordDetected.getPosList();
    for (RectF itemRect : rectList) {
      final DetectOverlay detectOverlay = new DetectOverlay(mContext, wordDetected.getWord(), itemRect);
      RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) itemRect.width(),
              (int) itemRect.height());
      if (wordDetected.isPaint()) {
        detectOverlay.setBackgroundResource(R.drawable.bg_detect);
        detectOverlay.setAlpha(0.95f);
      }
      layoutParams.leftMargin = (int) itemRect.left;
      layoutParams.topMargin = (int) itemRect.top;
      detectOverlay.setLayoutParams(layoutParams);
      detectOverlay.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          if (listener != null) listener.onClick(detectOverlay);
        }
      });
      activity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          addView(detectOverlay);
        }
      });
    }
  }

  public void detectWord(String jsonStr, OnDetectWordListener listener) {
    if (jsonStr == null) return;
    try {
      JSONObject jsonObject = new JSONObject(jsonStr);
      Iterator<String> keys = jsonObject.keys();
      for (Iterator<String> it = keys; it.hasNext(); ) {
        String item = it.next();
        JSONObject jsonObject1 = jsonObject.getJSONObject(item);
        JSONArray jsonArray = jsonObject1.getJSONArray("pos");
        RectF[] rectList = new RectF[jsonArray.length()];
        boolean isPaint = jsonObject1.getBoolean("show");
        Log.d("AppLog", item + " " + isPaint);
        for (int i = 0; i < jsonArray.length(); i++) {

          JSONArray item2 = jsonArray.getJSONArray(i);
          int left = item2.getInt(0);
          int top = item2.getInt(1);
          int width = item2.getInt(2);
          int height = item2.getInt(3);

          rectList[i] = new RectF();
          rectList[i].left = left * ratio;
          rectList[i].top = top * ratio;
          rectList[i].bottom = (top + height) * ratio;
          rectList[i].right = (left + width) * ratio;
        }
        WordDetected wordDetected = new WordDetected(item, rectList, isPaint);
        mOnDetectWordListener.onProgress(wordDetected);
      }
      listener.onCompleted(wordDetectedList);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void setRatio(float ratioWidth) {
    this.ratio = ratioWidth;
  }

  public void onClickDetectedWordListener(OnClickDetectedWordListener listener) {
    this.listener = listener;
  }

  public interface OnClickDetectedWordListener {
    void onClick(DetectOverlay view);
  }
}
