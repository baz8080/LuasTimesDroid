package com.mbcdev.nextluas.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.mbcdev.nextluas.R;

public class EditPreferenceActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
