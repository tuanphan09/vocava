package com.bit.vocava.models;

public class WordInfo {

  String word;
  int newScore;
  int oldScore;

  public WordInfo(String word, int newScore, int oldScore) {
    this.word = word;
    this.newScore = newScore;
    this.oldScore = oldScore;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public int getNewScore() {
    return newScore;
  }

  public void setNewScore(int newScore) {
    this.newScore = newScore;
  }

  public int getOldScore() {
    return oldScore;
  }

  public void setOldScore(int oldScore) {
    this.oldScore = oldScore;
  }

  public int getScoreIncrease() {
    return newScore - oldScore;
  }
}
