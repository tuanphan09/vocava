package com.bit.vocava.models;

public class LearningWord {

  private String word;
  private int score = 0;

  public LearningWord(String word, int score) {
    this.word = word;
    this.score = score;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
