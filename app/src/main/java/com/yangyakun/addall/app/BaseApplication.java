package com.yangyakun.addall.app;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by android on 2018/2/23.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, "ca-app-pub-6931183785201969~8933472191");
    }
}
