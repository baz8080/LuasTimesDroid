package com.mbcdev.nextluas.net;

import java.io.IOException;

import com.mbcdev.nextluas.model.ResultModel;

public interface LuasInfoConnector {

  ResultModel getStopInfo(String stopSuffix, String stopName) throws IOException;
}
