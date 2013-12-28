package com.mbcdev.nextluas.sorting;

import com.mbcdev.nextluas.model.StopInformationModel;

import java.util.Comparator;

public class StopIndexComparator implements Comparator<StopInformationModel> {

	@Override
	public int compare(StopInformationModel stop1, StopInformationModel stop2) {
		
		if (stop1.getStopNumber() < stop2.getStopNumber()) {
			return -1;
		} else if (stop1.getStopNumber() == stop2.getStopNumber()) {
			return 0;
		} else {
			return 1;
		}
		
	}

}
