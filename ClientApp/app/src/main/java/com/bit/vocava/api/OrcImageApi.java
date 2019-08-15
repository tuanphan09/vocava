package com.bit.vocava.api;

import com.bit.autodetectwordview.WordDetected;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.bit.vocava.AppConstant.IP;


public class OrcImageApi {

  public static String getWordFromImage(String pathImage) {
    List<WordDetected> wordDetecteds = new ArrayList<>();
    HttpClient client = new DefaultHttpClient();
    HttpPost post = new HttpPost("http://" + IP + ":3000/api/ocr-image");

    File file = new File(pathImage);
    String imageFileName = file.getName();

    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    builder.addBinaryBody
            ("file", file, ContentType.DEFAULT_BINARY, imageFileName);
    HttpEntity entity = builder.build();
    post.setEntity(entity);
    HttpResponse response = null;
    try {
      response = client.execute(post);
      return EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
