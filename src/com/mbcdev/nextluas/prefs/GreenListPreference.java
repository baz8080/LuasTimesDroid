package com.mbcdev.nextluas.prefs;

import com.mbcdev.nextluas.constants.StopConstants;

import android.content.Context;
import android.util.AttributeSet;

public class GreenListPreference extends MultiSelectListPreference {

	public GreenListPreference(Context context, AttributeSet attrs) {
		super(context, attrs, StopConstants.getStopNameArray(StopConstants.getGreenStops()));
	}

}
