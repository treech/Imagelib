package com.rcs.beautifylib.model;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.rcs.beautifylib.ui.activity.BLMosaicActivity;
import com.rcs.beautifylib.utils.ActivityUtils;

public class BLMosaicParam implements Parcelable{

    public static final String KEY = "mosaic";
    public static final int REQUEST_CODE_MOSAIC = 0x012;
    private String mPath;
    public static Bitmap mBitmap;

    public BLMosaicParam() {
    }

    public BLMosaicParam(String path) {
        this.mPath = path;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public static void recycleBitmap() {
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    public static void startActivity(Activity activity, BLMosaicParam param){
        Intent intent = new Intent(activity, BLMosaicActivity.class);
        intent.putExtra(BLMosaicParam.KEY, param);
        ActivityUtils.startActivityForResult(activity, intent, BLMosaicParam.REQUEST_CODE_MOSAIC);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPath);
    }

    protected BLMosaicParam(Parcel in) {
        mPath = in.readString();
    }

    public static final Creator<BLMosaicParam> CREATOR = new Creator<BLMosaicParam>() {
        @Override
        public BLMosaicParam createFromParcel(Parcel in) {
            return new BLMosaicParam(in);
        }

        @Override
        public BLMosaicParam[] newArray(int size) {
            return new BLMosaicParam[size];
        }
    };
}
