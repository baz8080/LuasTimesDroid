package com.mbcdev.nextluas.net;

import com.mbcdev.nextluas.model.StopTimes;

import java.io.IOException;

public interface LuasInfoConnector {

  StopTimes getStopTimes(int stopNumber, String stopName) throws IOException;
}
