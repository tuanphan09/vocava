package com.bit.vocava.api;

import com.bit.vocava.models.LearningWord;
import com.bit.vocava.models.MyUserInfo;

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

public class UserInfoApi {

  public static MyUserInfo getUserInfo() {
    HttpClient client = new DefaultHttpClient();
    HttpGet get = new HttpGet("http://"+IP+":3000/user-info?user-id=1");
    try {
      HttpResponse response = client.execute(get);
      JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
      MyUserInfo myUserInfo = new MyUserInfo();
      myUserInfo.setLevel(jsonObject.getInt("level"));

      JSONArray array = jsonObject.getJSONArray("learning_words");
      List<LearningWord> learningWords = new ArrayList<>();
      for (int i = 0; i < array.length(); i++) {
        JSONObject obj = array.getJSONObject(i);
        learningWords.add(new LearningWord(obj.getString("word"), obj.getInt("ma_score")));
      }
      myUserInfo.setList(learningWords);

      JSONArray array1 = jsonObject.getJSONArray("exam_score");
      double[] scoreExam = new double[array1.length()];
      for (int i = 0; i < array1.length(); i++) {
        scoreExam[i] = array1.getDouble(i);
      }
      myUserInfo.setScoreExam(scoreExam);
      return myUserInfo;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }
}
