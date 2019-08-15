package com.bit.vocava.models;

public class PuzzleTest extends TestQuestion {

  private String answer;

  public PuzzleTest(String word, String question, String[] options, String answer) {
    super(word, question, options);
    this.answer = answer;
  }

  @Override
  public String getType() {
    return PUZZLE_TEST;
  }

  @Override
  public Object getAnswer() {
    return answer;
  }
}
