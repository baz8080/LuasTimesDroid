package com.mbcdev.nextluas.sorting;

import com.mbcdev.nextluas.model.StopInformationModel;

import java.util.Comparator;

public class DistanceComparator implements Comparator<StopInformationModel> {

	@Override
	public int compare(StopInformationModel stop1, StopInformationModel stop2) {
		
		if (stop1.getDistanceFromCurrent() < stop2.getDistanceFromCurrent()) {
			return -1;
		} else if (stop1.getDistanceFromCurrent() == stop2.getDistanceFromCurrent()) {
			return 0;
		} else {
			return 1;
		}
	
	}
}
