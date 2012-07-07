package com.mbcdev.nextluas;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.mbcdev.nextluas.net.LocalTranscodeSiteConnector;
import com.mbcdev.nextluas.net.LuasInfoConnector;

public class MainModule extends AbstractModule {
 
  @Override
  protected void configure() {
    bind(LuasInfoConnector.class).to(LocalTranscodeSiteConnector.class).in(Singleton.class);
  }
}
