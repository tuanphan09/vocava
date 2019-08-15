package com.bit.vocava.fragments;

import android.content.Context;
import android.os.Bundle;

import java.lang.reflect.Field;

import me.yokeyword.swipebackfragment.SwipeBackFragment;

public class BaseFragment extends SwipeBackFragment {
  public final String FRAGMENT_TAG = getClass().getSimpleName();
  public ChangeFragmentListener mChangeFragmentListener;

  @Override public void onAttach(Context activity) {
    super.onAttach(activity);
    if (!(activity instanceof ChangeFragmentListener)) {
      throw new ClassCastException("activity ChangeFragmentListener");
    }
    mChangeFragmentListener = ((ChangeFragmentListener) activity);
  }

  @Override public void onDetach() {
    super.onDetach();
    mChangeFragmentListener = null;
  }

  @Override public void onDestroy() {
    try {
      Field mSwipeBackLayout = SwipeBackFragment.class.getDeclaredField("mSwipeBackLayout");
      mSwipeBackLayout.setAccessible(true);
      mSwipeBackLayout.set(this, null);
    } catch (NoSuchFieldException e) {

      e.printStackTrace();
    } catch (IllegalAccessException e) {

      e.printStackTrace();
    }

    try {
      Field mNoAnim = SwipeBackFragment.class.getDeclaredField("mNoAnim");
      mNoAnim.setAccessible(true);
      mNoAnim.set(this, null);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    super.onDestroy();
  }

  public void onBackPressed() {
    assert getFragmentManager() != null;
    getFragmentManager().popBackStack();
  }

  public interface ChangeFragmentListener {
    void addFragment(BaseFragment fragment, int animation);

    void addFragmentWithTarget(BaseFragment parent, BaseFragment child, int animation,
                               int fragmentCode);

    void addFragment(BaseFragment fragment, int animation, int resId);

    void changeFragment(BaseFragment fragment, int animation);

    void changeFragment(BaseFragment fragment, int animation, int resId);

    void replaceChildFragment(BaseFragment fragment, int animation, BaseFragment parentFragment);

    void replaceChildFragment(BaseFragment fragment, int animation, BaseFragment parentFragment,
                              int resId);

    void popBackStackInclusiveFragment(String stackName);

    void popBackStackFragment(String stackName);

    void popToRootFragments();

    void popBackStackFragments();

    boolean existForegroundFragment(String tag);
  }

  public interface OnCloseListener {
    void onClose();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    super.onSaveInstanceState(outState);
  }
}
