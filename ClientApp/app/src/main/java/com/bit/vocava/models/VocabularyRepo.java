package com.bit.vocava.models;

import com.bit.vocava.api.VocabularyApi;

import java.util.List;

public class VocabularyRepo {

  private static VocabularyRepo instance;
  private List<Vocabulary> vocabularyList;

  public static VocabularyRepo getInstance() {
    if (instance == null) instance = new VocabularyRepo();
    return instance;
  }

  public List<Vocabulary> getVocabularyList(OnGetListVocabularyListener listener) {
    if (vocabularyList == null) {
      listener.onStart();
      new Thread(() -> {
        vocabularyList = VocabularyApi.getListVocabulary();
        listener.onCompleted();
      }).start();

    }
    return vocabularyList;
  }

  public interface OnGetListVocabularyListener {
    void onStart();
    void onCompleted();
  }
}
