package com.bit.vocava.activities;

import android.os.Bundle;

import com.bit.vocava.R;
import com.bit.vocava.fragments.BaseFragment;
import com.bit.vocava.fragments.ExamFragment;
import com.bit.vocava.fragments.HomeFragment;
import com.bit.vocava.models.MyUserManager;

import java.util.Objects;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MyUserManager.getInstance(this);
    addFragment(new HomeFragment(), 0);
  }

  @Override
  public void onBackPressed() {
      BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
      Objects.requireNonNull(fragment).onBackPressed();
  }
}
