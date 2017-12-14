package com.mbcdev.nextluas.model;

public class StopInformationModel {

    private String displayName;

    private int stopNumber;

    public StopInformationModel(String displayName, int stopNumber) {
        this.displayName = displayName;
        this.stopNumber = stopNumber;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public String toString() {
        return this.displayName;
    }
}
