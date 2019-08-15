package com.bit.vocava.utils;

import android.media.ExifInterface;

import java.io.IOException;

public class BitmapUtils {

  public static int getOrientalBitmap(String path) {
    ExifInterface exif = null;
    try {
      exif = new ExifInterface(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED);
    return orientation;
  }
}
