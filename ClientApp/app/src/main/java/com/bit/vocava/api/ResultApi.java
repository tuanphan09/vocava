package com.bit.vocava.api;

import android.util.Log;

import androidx.constraintlayout.solver.GoalRow;

import com.bit.vocava.models.ResultInfo;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import static com.bit.vocava.AppConstant.IP;

public class ResultApi {

  public static String postResult(List<ResultInfo> resultInfos) {

    InputStream inputStream = null;
    String result = "";
    try {

      // 1. create HttpClient
      HttpClient httpclient = new DefaultHttpClient();

      // 2. make POST request to the given URL
      HttpPost httpPost = new HttpPost("http://"+IP+":3000/exam-result?user-id=1");

      String json = "";

      // 3. build jsonObject

      // 4. convert JSONObject to JSON to String
      json = new Gson().toJson(resultInfos);

      // ** Alternative way to convert Person object to JSON string usin Jackson Lib
      // ObjectMapper mapper = new ObjectMapper();
      // json = mapper.writeValueAsString(person);

      // 5. set json to StringEntity
      StringEntity se = new StringEntity(json);

      // 6. set httpPost Entity
      httpPost.setEntity(se);

      // 7. Set some headers to inform server about the type of the content
      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");

      // 8. Execute POST request to the given URL
      HttpResponse httpResponse = httpclient.execute(httpPost);

      // 9. receive response as inputStream
      return EntityUtils.toString(httpResponse.getEntity());
    } catch (Exception e) {
      Log.d("InputStream", e.getLocalizedMessage());
    }
    return "{}";
  }
}
