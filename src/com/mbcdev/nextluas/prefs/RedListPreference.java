package com.mbcdev.nextluas.prefs;

import com.mbcdev.nextluas.constants.StopConstants;

import android.content.Context;
import android.util.AttributeSet;

public class RedListPreference extends MultiSelectListPreference {

	public RedListPreference(Context context, AttributeSet attrs) {
		
		super(context, attrs, StopConstants.getStopNameArray(StopConstants.getRedStops()));
		
	}

}
