package com.bit.vocava.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bit.vocava.R;
import com.bit.vocava.activities.SpeechActivity;
import com.bit.vocava.adapters.ViewPagerHomeAdapter;
import com.bit.vocava.callback.BasicProgressListener;
import com.bit.vocava.models.MyUserManager;
import com.bit.vocava.models.TestVocavaManager;
import com.bumptech.glide.Glide;
import com.timqi.sectorprogressview.ColorfulRingProgressView;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.AudioFile;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;
import com.vincent.filepicker.filter.entity.VideoFile;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devlight.io.library.ntb.NavigationTabBar;

import static android.app.Activity.RESULT_OK;
import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends TestFragment {


  @BindView(R.id.nav_tab_bar)
  NavigationTabBar navTabBar;
  @BindView(R.id.view_pager)
  ViewPager viewPager;

  public HomeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    viewPager.setAdapter(new ViewPagerHomeAdapter(getFragmentManager()));
    ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
    models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_extensive),
            ResourcesCompat.getColor(getResources(), R.color.fbutton_default_color, null))
            .title("").build());
    models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_learn),
            ResourcesCompat.getColor(getResources(), R.color.fbutton_color_green_sea, null))
            .title("").build());
    models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_exam),
            ResourcesCompat.getColor(getResources(), R.color.fbutton_color_carrot, null))
            .title("").build());
    models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_statistic),
            ResourcesCompat.getColor(getResources(), R.color.fbutton_color_wisteria, null))
            .title("").build());
    navTabBar.setModels(models);
    navTabBar.setViewPager(viewPager, 0);

    navTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
    navTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
    navTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
    navTabBar.setTypeface("fonts/custom_font.ttf");
    navTabBar.setIsBadged(true);
    navTabBar.setIsTitled(true);
    navTabBar.setBgColor(ResourcesCompat.getColor(getResources(), R.color.fbutton_color_wet_asphalt, null));
    navTabBar.setIsTinted(true);
    navTabBar.setIsBadgeUseTypeface(true);
    navTabBar.setBadgeBgColor(Color.RED);
    navTabBar.setBadgeTitleColor(Color.WHITE);
    navTabBar.setIsSwiped(true);
    navTabBar.setBadgeSize(10);
    navTabBar.setTitleSize(0);
    navTabBar.setIconSizeFraction(0.5f);
  }

  void onClickUploadFilePDF() {
    AndPermission.with(this)
            .runtime()
            .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
            .onGranted(data -> {
              pickFile();
            }).start();
  }


  private void pickFile() {
    Intent intent4 = new Intent(getContext(), NormalFilePickActivity.class);
    intent4.putExtra(Constant.MAX_NUMBER, 1);
    intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"txt", "doc", "docx", "pdf"});
    startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
  }

  @Override
  public void onBackPressed() {
    getActivity().finish();
  }
}
