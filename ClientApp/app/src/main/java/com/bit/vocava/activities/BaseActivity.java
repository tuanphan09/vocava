package com.bit.vocava.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bit.vocava.R;
import com.bit.vocava.fragments.BaseFragment;

public class BaseActivity extends AppCompatActivity implements BaseFragment.ChangeFragmentListener {
    public static final int ANIMATION_NONE = 0;
    public static final int ANIMATION_SLIDE_RIGHT_TO_LEFT = 1;
    public static final int ANIMATION_SLIDE_TOP_TO_BOTTOM = 2;
    public static final int ANIMATION_SLIDE_BOTTOM_TO_TOP = 3;
    public static final int ANIMATION_SLIDE_LEFT_TO_RIGHT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //    WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public void setFragment(BaseFragment fragment, int animation) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.add(R.id.fragment_container, fragment, fragment.FRAGMENT_TAG);
        transaction.commit();
    }

    private Context getContext() {
        return this;
    }

    @Override
    public void addFragmentWithTarget(BaseFragment parent, BaseFragment child, int animation,
                                      int fragmentCode) {
        child.setTargetFragment(parent, fragmentCode);
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.addToBackStack(child.FRAGMENT_TAG);
        transaction.add(R.id.fragment_container, child, child.FRAGMENT_TAG);
        transaction.commitAllowingStateLoss();
    }

    public void setFragment(BaseFragment fragment, int animation, int resId) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.add(resId, fragment, fragment.FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void addFragment(BaseFragment fragment, int animation) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void addFragment(BaseFragment fragment, int animation, int resId) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.add(resId, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Override
    public void changeFragment(BaseFragment fragment, int animation) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.replace(R.id.fragment_container, fragment, fragment.FRAGMENT_TAG);
        transaction.commit();
    }

    public void changeFragment(BaseFragment fragment, int animation, int resId) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.replace(resId, fragment, fragment.FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void replaceChildFragment(BaseFragment fragment, int animation,
                                     BaseFragment parentFragment) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.remove(parentFragment);
        transaction.add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void replaceChildFragment(BaseFragment fragment, int animation,
                                     BaseFragment parentFragment, int resId) {
        FragmentTransaction transaction = getCustomFragmentTransaction(animation);
        transaction.remove(parentFragment);
        transaction.add(resId, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Override
    public void popBackStackInclusiveFragment(String stackName) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(stackName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void popBackStackFragment(String stackName) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(stackName, 0);
    }

    @Override
    public void popBackStackFragments() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count != 1) {
            getSupportFragmentManager().popBackStack();
            BaseFragment fragment =
                    (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            fragment.onBackPressed();
        }
    }

    @Override
    public void popToRootFragments() {
        FragmentManager fm = getSupportFragmentManager();
        while (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        }
    }

    public boolean existForegroundFragment(@Nullable String tag) {
        FragmentManager manager = getSupportFragmentManager();
        int entryCount = manager.getBackStackEntryCount();
        if (entryCount > 0) {
            FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(entryCount - 1);
            String str = backEntry.getName();
            if (str != null && str.equals(tag)) {
                return true;
            }
        }

        return false;
    }

    private FragmentTransaction getCustomFragmentTransaction(int animation) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        return transaction;
    }
}
