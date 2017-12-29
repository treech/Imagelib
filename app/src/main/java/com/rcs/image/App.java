package com.rcs.image;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.rcs.beautifylib.utils.BLConfig;
import com.rcs.beautifylib.utils.BLConfigManager;

/**
 * Created by Administrator on 2017/4/25.
 */

public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new AppActivityLifecycleCallbacks());
        BLConfigManager.register(new BLConfig())
                .statusBarColor(Color.parseColor("#D50A6E"))    //设置状态栏颜色
                .toolBarColor(Color.parseColor("#d4237a"))  //设置toolbar颜色
                .primaryColor(Color.parseColor("#d4237a")); //设置应用primary颜色
    }

    private class AppActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.e(TAG, "lifecycle->onActivityCreated:" + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.e(TAG, "lifecycle->onActivityStarted:" + activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.e(TAG, "lifecycle->onActivityResumed:" + activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.e(TAG, "lifecycle->onActivityPaused:" + activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.e(TAG, "lifecycle->onActivityStopped:" + activity);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.e(TAG, "lifecycle->onActivitySaveInstanceState:" + activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.e(TAG, "lifecycle->onActivityDestroyed:" + activity);
        }
    }


}
