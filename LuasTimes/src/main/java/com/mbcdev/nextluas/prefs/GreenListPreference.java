package com.mbcdev.nextluas.prefs;

import android.content.Context;
import android.util.AttributeSet;

import com.mbcdev.nextluas.constants.StopConstants;

public class GreenListPreference extends MultiSelectListPreference {

	public GreenListPreference(Context context, AttributeSet attrs) {
		super(context, attrs, StopConstants.getStopNameArray(StopConstants.getGreenStops()));
	}

}
