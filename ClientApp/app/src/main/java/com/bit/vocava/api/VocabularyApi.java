package com.bit.vocava.api;

import android.util.Log;

import com.bit.vocava.models.Vocabulary;

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

import javax.net.ssl.HttpsURLConnection;

import static com.bit.vocava.AppConstant.IP;

public class VocabularyApi {

  public static List<Vocabulary> getListVocabulary() {
    List<Vocabulary> list = new ArrayList<>();
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet("http://" + IP + ":3000/api/dictionary");
    try {
      HttpResponse response = client.execute(httpGet);
      if (response.getStatusLine().getStatusCode()== HttpsURLConnection.HTTP_OK) {
        String jsonStr = EntityUtils.toString(response.getEntity());
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonObject = jsonArray.getJSONObject(i);
          String word = jsonObject.getString("index");
          list.add(getVocabulary(word));
          Log.d("AppLog", "index : " + i);
        }
        return list;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return list;
  }

  public static Vocabulary getVocabulary(String word) {

    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet("http://" + IP + ":3000/api/get-oxford-result?word=" + word);
    try {
      HttpResponse response = client.execute(httpGet);
      if (response.getStatusLine().getStatusCode()== HttpsURLConnection.HTTP_OK) {
        String jsonStr = EntityUtils.toString(response.getEntity());
        JSONObject vocabularyJson = new JSONObject(jsonStr);
        JSONArray entriesList = vocabularyJson.getJSONArray("entries");

        int lenght = entriesList.length();
        String[] category = new String[lenght];
        String[] definitions = new String[lenght];
        String[] shortDefinitions = new String[lenght];
        String audioFile = "";
        String spell = "";
        String example = "";
        String exampleAI = vocabularyJson.getString("example");
        boolean isFirstTime = true;
        for (int i = 0; i < entriesList.length(); i++) {
          JSONObject entry = entriesList.getJSONObject(i);
          category[i] = entry.getString("category");
          definitions[i] = entry.getString("definitions");
          shortDefinitions[i] = entry.getString("shortDefinitions");
          example = entry.getString("examples");
          if (isFirstTime) {
            JSONObject pronunciations = entry.getJSONObject("pronunciations");
            audioFile = pronunciations.getString("audioFile");
            spell = pronunciations.getString("phoneticSpelling");
            isFirstTime = false;
            JSONArray subsense = entry.getJSONArray("subsenses");
          }
        }
        Log.d("AppLog", "Short definition : " + shortDefinitions[0]);
        return new Vocabulary(word, category, definitions, shortDefinitions, spell, example, exampleAI, audioFile);
      } else return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }
}
