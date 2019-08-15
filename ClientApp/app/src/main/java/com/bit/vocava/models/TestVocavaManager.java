package com.bit.vocava.models;

import com.bit.vocava.api.TestApi;
import com.bit.vocava.callback.BasicProgressListener;

import java.util.ArrayList;
import java.util.List;

public class TestVocavaManager {

  private static TestVocavaManager instance;
  private List<TestQuestion> questions;
  private int currentIndexQuestion = -1;
  private List<ResultInfo> resultInfos;
  private List<String> words;

  public static TestVocavaManager getInstance() {
    if (instance == null) instance = new TestVocavaManager();
    return instance;
  }

  public void initTest(BasicProgressListener listener) {
    listener.onStart();
    new Thread(() -> {
      questions = TestApi.getTestQuestions("1");
      resultInfos = new ArrayList<>();
      words = new ArrayList<>();
      for (TestQuestion testQuestion : questions) {
        String word = testQuestion.getWord();
        if (!words.contains(word)) {
          words.add(word);
          resultInfos.add(new ResultInfo(word));
        }
      }
      currentIndexQuestion = 0;
      listener.onCompleted();
    }).start();

  }

  public int getCurrentIndexQuestion() {
    return currentIndexQuestion;
  }

  public TestQuestion getCurrentQuestion() {
    if (questions == null) return null;
    return questions.get(currentIndexQuestion);
  }

  public TestQuestion nextQuestion() {
    currentIndexQuestion++;
    if (currentIndexQuestion == questions.size()) {
      currentIndexQuestion = -1;
      questions.clear();
      return null;
    }
    return questions.get(currentIndexQuestion);
  }

  public List<ResultInfo> getResultInfos() {
    return resultInfos;
  }

  public void onHaveCorrectAnswer() {
    TestQuestion question = getCurrentQuestion();
    int index = words.indexOf(question.getWord());
    resultInfos.get(index).getAnswer().add(1);
  }

  public void onHaveWrongAnswer() {
    TestQuestion question = getCurrentQuestion();
    int index = words.indexOf(question.getWord());
    resultInfos.get(index).getAnswer().add(0);
  }

  public int getNumberQuestion() {
    return questions.size();
  }
}
