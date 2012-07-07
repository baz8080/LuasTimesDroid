package com.mbcdev.nextluas.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.mbcdev.nextluas.R;

public class EditPreferenceActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		
		String defaultClosestKey = getString(R.string.prefDefaultClosestKey);
		
		if (!prefs.contains(defaultClosestKey)) {
			editor.putBoolean(defaultClosestKey, true);
		}
		
		editor.commit();
		
		addPreferencesFromResource(R.xml.prefs);
		
	}
}
