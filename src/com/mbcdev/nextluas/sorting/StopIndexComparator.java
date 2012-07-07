package com.mbcdev.nextluas.sorting;

import java.util.Comparator;

import com.mbcdev.nextluas.model.StopInformationModel;

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
