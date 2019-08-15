package com.bit.vocava.models;

public class Vocabulary {

  private String word;
  private String[] category;
  private String[] definitions;
  private String[] shortDefinitions;
  private String spelling;
  private String example;
  private String exampleAI;
  private String audioFile;
  private float percent;
  private int level;

  public Vocabulary() {
  }

  public Vocabulary(String word, String[] category, String[] definitions, String[] shortDefinitions, String spelling, String example, String exampleAI, String audioFile) {
    this.word = word;
    this.category = category;
    this.definitions = definitions;
    this.shortDefinitions = shortDefinitions;
    this.spelling = spelling;
    this.example = example;
    this.exampleAI = exampleAI;
    this.audioFile = audioFile;
  }

  public String getWord() {
    return word;
  }

  public String[] getCategory() {
    return category;
  }

  public String[] getDefinitions() {
    return definitions;
  }

  public String[] getShortDefinitions() {
    return shortDefinitions;
  }

  public String getSpelling() {
    return spelling;
  }

  public String getAudioFile() {
    return audioFile;
  }

  public float getPercent() {
    return percent;
  }

  public int getLevel() {
    return level;
  }

  public String getExample() {
    if (exampleAI != null && ! exampleAI.isEmpty()) return exampleAI;
    else if (example != null && ! example.isEmpty()) return example;
    else return "";
  }
}
