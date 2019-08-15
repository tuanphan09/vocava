package com.bit.vocava.models;

public class SentenceTest extends TestQuestion {

  int answer;

  public SentenceTest(String word, String question, String[] options, int answer) {
    super(word, question, options);
    this.answer = answer;
  }

  @Override
  public String getType() {
    return SENTENCE_TEST;
  }

  @Override
  public Object getAnswer() {
    return answer;
  }
}
