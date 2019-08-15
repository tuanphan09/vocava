package com.bit.vocava.models;

import android.content.Context;
import android.content.SharedPreferences;

public class MyUserManager {

  int level = 1;
  int percent = 0;

  private static MyUserManager instance;

  public static MyUserManager getInstance(Context context) {
    if (instance == null) {
      instance = new MyUserManager();
      SharedPreferences preferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE);
      if (preferences != null) {
        instance.level = preferences.getInt("level", 1);
        instance.percent = preferences.getInt("percent", 0);
      }
    }
    return instance;
  }

  public MyUserManager() {
  }

  public void onHaveCorrectAnswer(int bonus) {
    percent += bonus;
    if (percent == 100) {
      level++;
      percent = 0;
    }
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getPercent() {
    return percent;
  }

  public void setPercent(int percent) {
    this.percent = percent;
  }
}
