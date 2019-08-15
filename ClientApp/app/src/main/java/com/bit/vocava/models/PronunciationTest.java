package com.bit.vocava.models;

public class PronunciationTest extends TestQuestion {

  int answer;

  public PronunciationTest(String word, String question, String[] options, int answer) {
    super(word, question, options);
    this.answer = answer;
  }

  @Override
  public String getType() {
    return PRONUNCIATION_TEST;
  }

  @Override
  public Object getAnswer() {
    return answer;
  }
}
