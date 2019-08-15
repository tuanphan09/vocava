package com.bit.vocava.models;

import java.util.List;

public class MyUserInfo {

  private int level = 0;
  private List<LearningWord> list;
  private double[] scoreExam;

  public MyUserInfo() {
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public List<LearningWord> getList() {
    return list;
  }

  public void setList(List<LearningWord> list) {
    this.list = list;
  }

  public double[] getScoreExam() {
    return scoreExam;
  }

  public void setScoreExam(double[] scoreExam) {
    this.scoreExam = scoreExam;
  }
}
