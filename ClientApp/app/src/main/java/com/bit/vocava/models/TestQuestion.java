package com.bit.vocava.models;

public abstract class TestQuestion {

  public static final String DEFINITION_TEST = "definition_task";
  public static final String PRONUNCIATION_TEST = "pronunciation_task";
  public static final String SENTENCE_TEST = "sentence_task";
  public static final String PUZZLE_TEST = "puzzle_task";

  private String word;
  private String question;
  private String[] options;
  private String type;

  public TestQuestion(String word, String question, String[] options) {
    this.word = word;
    this.question = question;
    this.options = options;
  }

  public String getWord() {
    return word;
  }

  public String getQuestion() {
    return question;
  }

  public String[] getOptions() {
    return options;
  }

  public abstract String getType();
  public abstract Object getAnswer();
}
