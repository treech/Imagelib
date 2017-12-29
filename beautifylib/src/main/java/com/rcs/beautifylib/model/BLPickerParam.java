package com.rcs.beautifylib.model;

import android.app.Activity;

import com.rcs.beautifylib.ui.activity.BLPhotoPickActivity;
import com.rcs.beautifylib.utils.ActivityUtils;

/**
 * Created by Administrator on 2017/4/23.
 */

public class BLPickerParam {

    public static final int REQUEST_CODE_PHOTO_PICKER = 0x013;

    public static void startActivity(Activity activity){
        ActivityUtils.startActivityForResult(activity, BLPhotoPickActivity.class, REQUEST_CODE_PHOTO_PICKER);
    }
}
