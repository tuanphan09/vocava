package com.bit.vocava.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bit.vocava.R;
import com.bit.vocava.activities.SpeechActivity;
import com.bit.vocava.callback.OnCustomClickListener;
import com.bit.vocava.utils.PhotorTool;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.AudioFile;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExtensiveFragment extends BaseFragment {

  @BindView(R.id.btn_audio)
  View btnAudio;
  @BindView(R.id.btn_camera)
  View btnCamera;

  public ExtensiveFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_extensive, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    PhotorTool.clickScaleView(btnAudio, (v, event) -> {
      onClickUploadFileAudio();
    });
    PhotorTool.clickScaleView(btnCamera, (v, event) -> {
      onClickUploadPhoto();
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case Constant.REQUEST_CODE_PICK_IMAGE:
        if (resultCode == RESULT_OK) {
          ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
          if (list == null || list.isEmpty()) break;
          mChangeFragmentListener.addFragment(DetectPhotoFragment.newInstance(list.get(0).getPath()), 0);
        }
        break;
      case Constant.REQUEST_CODE_PICK_AUDIO:
        if (resultCode == RESULT_OK) {
          ArrayList<AudioFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_AUDIO);
          Log.d("AppLog", "File Path:" + list.get(0).getPath());
          Intent intent = new Intent(getContext(), SpeechActivity.class);
          intent.putExtra("pathAudio", list.get(0).getPath());
          startActivity(intent);
        }
        break;
    }
  }

  void onClickUploadFileAudio() {
    AndPermission.with(this)
            .runtime()
            .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
            .onGranted(data -> {
              pickAudio();
            }).start();
  }

  void onClickUploadPhoto() {
    AndPermission.with(this)
            .runtime()
            .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
            .onGranted(data -> {
              pickPhoto();
            }).start();
  }

  private void pickPhoto() {
    Intent intent1 = new Intent(getContext(), ImagePickActivity.class);
    intent1.putExtra(IS_NEED_CAMERA, true);
    intent1.putExtra(Constant.MAX_NUMBER, 1);
    startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
  }

  private void pickAudio() {
    Intent intent3 = new Intent(getContext(), AudioPickActivity.class);
    intent3.putExtra(IS_NEED_RECORDER, true);
    intent3.putExtra(Constant.MAX_NUMBER, 1);
    startActivityForResult(intent3, Constant.REQUEST_CODE_PICK_AUDIO);
  }
}
