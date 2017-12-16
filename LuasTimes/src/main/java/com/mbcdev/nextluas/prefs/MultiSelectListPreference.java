package com.mbcdev.nextluas.prefs;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class MultiSelectListPreference extends ListPreference {

	public static final String SEPARATOR = "@,@";

	private boolean[] clickedIndices;

	MultiSelectListPreference(Context context, AttributeSet attrs, CharSequence[] stopNames) {
		super(context, attrs);

		setEntries(stopNames);
		setEntryValues(stopNames);

		clickedIndices = new boolean[getEntries().length];
	}

	@Override
	public void setEntries(CharSequence[] entries) {
		super.setEntries(entries);
		clickedIndices = new boolean[entries.length];
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		CharSequence[] entries = getEntries();
		CharSequence[] entryValues = getEntryValues();

		if (entries == null || entryValues == null
				|| entries.length != entryValues.length) {
			throw new IllegalStateException(
					"filter names and values are not the same length!");
		}

		restoreCheckedEntries();

		builder.setMultiChoiceItems(entries, clickedIndices,
				new DialogInterface.OnMultiChoiceClickListener() {
					public void onClick(DialogInterface dialog, int which,
							boolean val) {
						clickedIndices[which] = val;
					}
				});
	}

	static String[] parseStoredValue(CharSequence val) {
		if ("".equals(val))
			return null;
		else
			return ((String) val).split(SEPARATOR);
	}

	private void restoreCheckedEntries() {
		CharSequence[] entryValues = getEntryValues();

		String[] vals = parseStoredValue(getValue());
		if (vals != null) {
            for (String val1 : vals) {
                String val = val1.trim();
                for (int i = 0; i < entryValues.length; i++) {
                    CharSequence entry = entryValues[i];
                    if (entry.equals(val)) {
                        clickedIndices[i] = true;
                        break;
                    }
                }
            }
		}
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// super.onDialogClosed(positiveResult);
		
		CharSequence[] entryValues = getEntryValues();
		if (positiveResult && entryValues != null) {
			StringBuffer value = new StringBuffer();
			for (int i = 0; i < entryValues.length; i++) {
				if (clickedIndices[i]) {
					value.append(entryValues[i]).append(SEPARATOR);
				}
			}
			
			if (callChangeListener(value)) {
				String val = value.toString();
				if (val.length() > 0)
					val = val.substring(0, val.length() - SEPARATOR.length());
				setValue(val);
			}
		}
	}

}
