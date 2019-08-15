package com.bit.vocava.models;

import java.util.ArrayList;
import java.util.List;

public class ResultInfo {

  String word;
  List<Integer> answer;

  public ResultInfo(String word, List<Integer> answer) {
    this.word = word;
    this.answer = answer;
  }

  public ResultInfo(String word) {
    this.word = word;
    answer = new ArrayList<>();
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public List<Integer> getAnswer() {
    return answer;
  }

  public void setAnswer(List<Integer> answer) {
    this.answer = answer;
  }
}
