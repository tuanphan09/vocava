package com.bit.vocava.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.bit.vocava.AppConstant.IP;

public class StatisticApi {

  public static JSONObject getStatistic() {
    HttpClient client = new DefaultHttpClient();
    HttpGet get = new HttpGet("http://" + IP + ":3000/statistic?user-id=1");
    try {
      HttpResponse response = client.execute(get);
      return new JSONObject(EntityUtils.toString(response.getEntity()));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }
}
