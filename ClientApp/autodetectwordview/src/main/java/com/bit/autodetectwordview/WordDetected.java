package com.bit.autodetectwordview;

import android.graphics.RectF;

import java.util.List;

public class WordDetected {

   private String word;
   private RectF[] posList;
   private boolean isPaint;

  public WordDetected(String word, RectF[] posList, boolean isPaint) {
    this.word = word;
    this.posList = posList;
    this.isPaint = isPaint;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public RectF[] getPosList() {
    return posList;
  }

  public void setPosList(RectF[] posList) {
    this.posList = posList;
  }

  public boolean isPaint() {
    return isPaint;
  }
}
