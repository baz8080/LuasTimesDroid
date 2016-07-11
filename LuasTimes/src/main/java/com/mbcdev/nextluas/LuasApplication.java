package com.mbcdev.nextluas;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class LuasApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_unit_id));
    }
}
