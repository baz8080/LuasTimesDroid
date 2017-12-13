package com.mbcdev.nextluas.model;

public class StopInformationModel {

	private String displayName;
	
	private int stopNumber;

	public String getDisplayName() {
		return displayName;
	}

	public StopInformationModel(String displayName) {
		this(displayName, 0);
	}

	public StopInformationModel(String displayName, int stopNumber) {
		this.displayName = displayName;
		this.stopNumber = stopNumber;
	}
		
	public String toString() {
		return this.displayName;
	}

	public int getStopNumber() {
		return stopNumber;
	}
}
