package com.bit.vocava.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bit.vocava.fragments.ExamFragment;
import com.bit.vocava.fragments.ExtensiveFragment;
import com.bit.vocava.fragments.LearningWordFragment;
import com.bit.vocava.fragments.StatisticFragment;

public class ViewPagerHomeAdapter extends FragmentPagerAdapter {

  public ViewPagerHomeAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new ExtensiveFragment();
      case 1:
        return new LearningWordFragment();
      case 2:
        return new ExamFragment();
      case 3:
        return new StatisticFragment();
      default:
        return new ExamFragment();
    }
  }

  @Override
  public int getCount() {
    return 4;
  }
}
