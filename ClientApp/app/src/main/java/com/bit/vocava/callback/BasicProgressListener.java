package com.bit.vocava.callback;

public interface BasicProgressListener {
  void onStart();
  void onProgress(float progress);
  void onCompleted();
}
