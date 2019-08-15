package com.bit.autodetectwordview;

import java.util.List;

public interface OnDetectWordListener {

  void onProgress(WordDetected wordDetected);
  void onCompleted(List<WordDetected> wordDetectedList);
}
