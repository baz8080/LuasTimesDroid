package com.mbcdev.nextluas;

import com.google.inject.Stage;

import roboguice.RoboGuice;
import android.app.Application;

public class MainApplication extends Application {
  
  @Override
  public void onCreate() {
    super.onCreate();
    RoboGuice.setBaseApplicationInjector(this, Stage.PRODUCTION);
  }
}
