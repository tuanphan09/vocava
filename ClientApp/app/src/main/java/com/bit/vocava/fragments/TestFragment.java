package com.bit.vocava.fragments;

import android.util.Log;

import com.bit.vocava.models.DefinitionTest;
import com.bit.vocava.models.PronunciationTest;
import com.bit.vocava.models.PuzzleTest;
import com.bit.vocava.models.SentenceTest;
import com.bit.vocava.models.TestQuestion;

class TestFragment extends BaseFragment{


  void addFragmentWithTestQuestion(TestQuestion testQuestion) {
    if (testQuestion == null) {
      mChangeFragmentListener.addFragment(new ResultFragment(), 0);
    }
    else {
      Log.d("AppLog", "current : " + testQuestion.getWord());
      switch (testQuestion.getType()) {
        case TestQuestion.DEFINITION_TEST:
          mChangeFragmentListener.addFragment(DefinitionTestFragment.newInstance((DefinitionTest) testQuestion), 0);
          break;
        case TestQuestion.PRONUNCIATION_TEST:
          mChangeFragmentListener.addFragment(PronunciationTestFragment.newInstance((PronunciationTest) testQuestion), 0);
          break;
        case TestQuestion.PUZZLE_TEST:
          mChangeFragmentListener.addFragment(PuzzleTestFragment.newInstance((PuzzleTest) testQuestion), 0);
          break;
        case TestQuestion.SENTENCE_TEST:
          mChangeFragmentListener.addFragment(DefinitionTestFragment.newInstance((DefinitionTest) testQuestion), 0);
          break;
      }
    }
  }

  @Override
  public void onBackPressed() {
    mChangeFragmentListener.popToRootFragments();
  }
}
