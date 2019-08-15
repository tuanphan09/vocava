package com.bit.vocava.models;

public class DefinitionTest extends TestQuestion {


  private int answer;

  public DefinitionTest(String word, String question, String[] options, int answer) {
    super(word, question, options);
    this.answer = answer;
  }

  @Override
  public String getType() {
    return DEFINITION_TEST;
  }

  @Override
  public Object getAnswer() {
    return answer;
  }
}
