package com.bit.vocava.api;

import android.util.Log;

import com.bit.vocava.models.DefinitionTest;
import com.bit.vocava.models.PronunciationTest;
import com.bit.vocava.models.PuzzleTest;
import com.bit.vocava.models.SentenceTest;
import com.bit.vocava.models.TestQuestion;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.bit.vocava.AppConstant.IP;

public class TestApi {

  public static List<TestQuestion> getTestQuestions(String userID) {
    List<TestQuestion> questions = new ArrayList<>();
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet("http://" + IP + ":3000/api/create-new-exam?user-id=" + userID);
    try {
      HttpResponse response = client.execute(httpGet);
      String testStr = EntityUtils.toString(response.getEntity());
      JSONArray questionsJsonArr = new JSONArray(testStr);
      String word;
      String answerStr;
      int answerInt;
      JSONArray arr;
      int length;
      String[] options;
      String question;
      JSONObject testJsonObj;
      String type;
      TestQuestion test = null;
      for (int i = 0; i < questionsJsonArr.length(); i++) {
        testJsonObj = questionsJsonArr.getJSONObject(i);
        type = testJsonObj.getString("type");

        switch (type){
          case "definition":
            break;
          case "definition_task":
            //get word
            word = testJsonObj.getString("word");
            //get answer
            answerInt = testJsonObj.getInt("answer");
            //get options
            arr = testJsonObj.getJSONArray("options");
            length = arr.length();
            options = new String[length];
            for (int j = 0; j < length; j++) options[j] = arr.getString(j);
            //get question
            question = testJsonObj.getString("question");
            test = new DefinitionTest(word, question, options, answerInt);
            questions.add(test);
            break;
          case "pronunciation_task":
            //get word
            word = testJsonObj.getString("word");
            //get answer
            answerInt = testJsonObj.getInt("answer");
            //get options
            arr = testJsonObj.getJSONArray("options");
            length = arr.length();
            options = new String[length];
            for (int j = 0; j < length; j++) options[j] = arr.getString(j);
            //get question
            question = testJsonObj.getString("question");
            test = new PronunciationTest(word, question, options, answerInt);
            questions.add(test);
            break;
          case "sentence_task":
            //get word
            word = testJsonObj.getString("word");
            //get answer
            answerInt = testJsonObj.getInt("answer");
            //get options
            arr = testJsonObj.getJSONArray("options");
            length = arr.length();
            options = new String[length];
            for (int j = 0; j < length; j++) options[j] = arr.getString(j);
            //get question
            question = testJsonObj.getString("question");
            test = new DefinitionTest(word, question, options, answerInt);
            questions.add(test);
            break;
          case "puzzle_task":
            //get word
            word = testJsonObj.getString("word");
            //get answer
            answerStr = testJsonObj.getString("answer");
            //get options
            arr = testJsonObj.getJSONArray("options");
            length = arr.length();
            options = new String[length];
            for (int j = 0; j < length; j++) options[j] = arr.getString(j);
            //get question
            question = testJsonObj.getString("question");
            test = new PuzzleTest(word, question, options, answerStr);
            questions.add(test);
            break;
        }
      }
      return questions;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
