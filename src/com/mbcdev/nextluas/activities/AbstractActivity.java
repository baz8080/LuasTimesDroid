package com.mbcdev.nextluas.activities;

import com.mbcdev.nextluas.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager.BadTokenException;

public abstract class AbstractActivity extends Activity {

  protected static final int UPDATE_MS = 15000;
  protected static final int UPDATE_DISTANCE = 10;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  protected void makeInfoDialogue(String error) {
    Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setTitle(R.string.connectorDialogTitle);

    alertDialog.setMessage(error);

    alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        // OK!
      }
    });
    
    try {
    	alertDialog.show();
    } catch (BadTokenException e) {
    	//Ln.e("We wanted to show a dialogue but the activity went away.");
    }
    
  }
}
