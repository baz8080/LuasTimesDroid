package com.mbcdev.nextluas.net;

import com.mbcdev.nextluas.model.ResultModel;

import java.io.IOException;

public interface LuasInfoConnector {

  ResultModel getStopInfo(String stopSuffix, String stopName) throws IOException;
}
