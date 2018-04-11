package com.mbcdev.nextluas.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mbcdev.nextluas.R;
import com.mbcdev.nextluas.constants.StopConstants;
import com.mbcdev.nextluas.model.ResultModel;
import com.mbcdev.nextluas.model.StopInformation;
import com.mbcdev.nextluas.model.StopTime;
import com.mbcdev.nextluas.net.LocalTranscodeSiteConnector;
import com.mbcdev.nextluas.net.LuasInfoConnector;
import com.mbcdev.nextluas.prefs.MultiSelectListPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NextLuasActivity extends Activity implements OnItemSelectedListener, ActionBar.OnNavigationListener {

    private static final String TAG = NextLuasActivity.class.getSimpleName();

    private static final int RED_LINE_NAV_INDEX = 0;
    private static final int GREEN_LINE_NAV_INDEX = 1;
    public static final String LAST_SELECTED_STOP_NAME = "last_selected_stop";

    private ConnectivityManager mConnectivityManager;
    private LuasInfoConnector mLuasConnector;

    private ResultModel mStopModel;
    final Handler mLookupHandler = new Handler();

    private ArrayAdapter<StopInformation> mRedAdapter;
    private ArrayAdapter<StopInformation> mGreenAdapter;

    private SharedPreferences mSharedPreferences;

    private Spinner stopSpinner;

    private TextView txtLastUpdated;

    private TableLayout inboundTable;

    private TableLayout outboundTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.luas_ads);

        stopSpinner = findViewById(R.id.stopSpinner);
        txtLastUpdated = findViewById(R.id.txtLastUpdated);
        inboundTable = findViewById(R.id.tblInbound);
        outboundTable = findViewById(R.id.tblOutbound);
        TextView txtProblem = findViewById(R.id.txtProblem);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        }

        SpinnerAdapter navigationSpinnerAdapter = ArrayAdapter.createFromResource(
                getActionBar().getThemedContext(),
                R.array.lineNamesArray,
                android.R.layout.simple_spinner_dropdown_item);

        getActionBar().setListNavigationCallbacks(navigationSpinnerAdapter, this);

        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mLuasConnector = new LocalTranscodeSiteConnector();

        mRedAdapter = fromStopModel(StopConstants.getRedStops());
        mGreenAdapter = fromStopModel(StopConstants.getGreenStops());

        String defaultLine = mSharedPreferences.getString(getString(R.string.prefDefaultLineKey), getString(R.string.redLine));

        int navIndexToSet = defaultLine.equals(getString(R.string.greenLine)) ? GREEN_LINE_NAV_INDEX : RED_LINE_NAV_INDEX;

        getActionBar().setSelectedNavigationItem(navIndexToSet);

        stopSpinner.setOnItemSelectedListener(this);

        txtProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NextLuasActivity.this, ProblemActivity.class));
            }
        });

        stopSpinner.setAdapter(getCurrentAdapter());
        handleFilter();
    }

    private int getStopPosition(List<StopInformation> StopInformations, String lastSelectedStopName) {
        for (StopInformation StopInformation : StopInformations) {
            if (StopInformation.getDisplayName().equals(lastSelectedStopName)) {
                return StopInformations.indexOf(StopInformation);
            }
        }
        return -1;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void handleFilter() {

        boolean lineFilterEnabled = isLineFilteringEnabled();

        // We want to reset the adapters in any case
        mRedAdapter = fromStopModel(StopConstants.getRedStops());
        mGreenAdapter = fromStopModel(StopConstants.getGreenStops());

        ArrayAdapter<StopInformation> currentAdapter = getCurrentAdapter();

        List<StopInformation> filteredStops = new ArrayList<>();

        if (lineFilterEnabled) {
            String[] filterList = null;

            if (redLine()) {
                filterList = mSharedPreferences.getString(getString(R.string.prefStopListRedKey), "").split(MultiSelectListPreference.SEPARATOR);
            } else if (greenLine()) {
                filterList = mSharedPreferences.getString(getString(R.string.prefStopListGreenKey), "").split(MultiSelectListPreference.SEPARATOR);
            }

            if (filterList.length == 0 || (filterList.length == 1 && filterList[0].equals(""))) {
                Log.d(TAG,"Filter enabled but no stops selected, defaulting to all");
            } else {

                for (String stopName : filterList) {
                    for (int i = 0; i < currentAdapter.getCount(); i++) {
                        if (stopName.equals(currentAdapter.getItem(i).getDisplayName())) {
                            filteredStops.add(currentAdapter.getItem(i));
                        }
                    }
                }

                currentAdapter = fromStopModel(filteredStops);
            }
        }

        stopSpinner.setAdapter(currentAdapter);

        int selectedPosition = 0;

        String lastSelectedStopName = mSharedPreferences.getString(LAST_SELECTED_STOP_NAME, null);
        if (lastSelectedStopName != null) {
            List<StopInformation> stops = filteredStops.size() > 0 ? filteredStops :
                    redLine() ? StopConstants.getRedStops() : StopConstants.getGreenStops();
            selectedPosition = getStopPosition(stops, lastSelectedStopName);
        }

        stopSpinner.setSelection(selectedPosition != -1 ? selectedPosition : 0);
    }

    private boolean isLineFilteringEnabled() {
        return mSharedPreferences.getBoolean(getString(R.string.prefFilterEnableKey), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuIdHelp:
                launchHelp();
                return true;
            case R.id.menuIdSettings:
                startActivityForResult(new Intent(this, EditPreferenceActivity.class), 8080);
                return true;
            case R.id.menuRefresh:
                handleRefreshButton();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 8080) {
            handleFilter();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (!networkAvailable()) {
            Toast.makeText(this, getString(R.string.noNetwork), Toast.LENGTH_SHORT).show();
            return;
        }

        StopInformation model = (StopInformation) parent.getItemAtPosition(pos);

        mSharedPreferences.edit()
                .putString(LAST_SELECTED_STOP_NAME, model.getDisplayName())
                .apply();

        lookupStopInfo(model);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    private void lookupStopInfo(final StopInformation model) {

        txtLastUpdated.setText(getText(R.string.updating));

        Thread t = new Thread() {
            public void run() {
                try {
                    mStopModel = mLuasConnector.getStopInfo(model.getStopNumber(), model.getDisplayName());

                    mLookupHandler.post(new Runnable() {
                        public void run() {
                            updateUI();
                        }
                    });

                } catch (IOException e) {
                    mLookupHandler.post(new ErrorRunnable(e));
                }
            }
        };
        t.start();
    }

    protected void updateUI() {

        if (mStopModel == null || (mStopModel.getInbound().isEmpty() && mStopModel.getOutbound().isEmpty())) {
            makeInfoDialogue("There was a problem getting the times, perhaps the times on luas.ie are missing?");
            resetUI();
            return;
        }

        txtLastUpdated.setText(String.format(getString(R.string.lastUpdate), mStopModel.getFormattedLastUpdated()));

        inboundTable.removeAllViews();
        addTimesToTable(inboundTable, mStopModel.getInbound());

        outboundTable.removeAllViews();
        addTimesToTable(outboundTable, mStopModel.getOutbound());
    }

    private boolean networkAvailable() {
        return mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
                || mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
    }

    private void resetUI() {
        inboundTable.removeAllViews();
        outboundTable.removeAllViews();
        txtLastUpdated.setText("");
    }

    private void handleRefreshButton() {
        StopInformation model = (StopInformation) stopSpinner.getSelectedItem();
        lookupStopInfo(model);
    }

    private void addTimesToTable(TableLayout layout, List<StopTime> stops) {

        int[] fontSizes = new int[] {
                (int)getResources().getDimension(R.dimen.lt_text_large),
                (int)getResources().getDimension(R.dimen.lt_text_medium),
                (int)getResources().getDimension(R.dimen.lt_text_small),
                (int)getResources().getDimension(R.dimen.lt_text_micro)
        };

        int stopNumber = 0;

        for (StopTime st : stops) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            TextView name = new TextView(this);
            name.setTextColor(getResources().getColor(android.R.color.secondary_text_light));
            name.setLayoutParams(lp);
            name.setText(st.getName());
            name.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizes[stopNumber]);

            TextView minutes = new TextView(this);
            minutes.setTextColor(getResources().getColor(android.R.color.secondary_text_light));
            minutes.setGravity(Gravity.RIGHT);
            minutes.setLayoutParams(lp);
            minutes.setText(st.getMinutes());
            minutes.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizes[stopNumber]);

            tr.addView(name);
            tr.addView(minutes);

            layout.addView(tr, lp);

            if (stopNumber != fontSizes.length - 1) {
                stopNumber++;
            }
        }

        layout.setColumnStretchable(0, true);
        layout.setColumnShrinkable(1, true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.edit().putString(getString(R.string.prefDefaultLineKey), navIndexToLabel()).apply();
    }

    private ArrayAdapter<StopInformation> fromStopModel(List<StopInformation> stopList) {
        ArrayAdapter<StopInformation> adapter = new ArrayAdapter<StopInformation>(this, android.R.layout.simple_spinner_item){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setPadding(0, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
                return view;
            }
        };

        for (StopInformation model : stopList) {
            adapter.add(model);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void launchHelp() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mbcdev.com/luas-times-help/"));
        startActivity(i);
    }

    private boolean redLine() {
        return RED_LINE_NAV_INDEX == getActionBar().getSelectedNavigationIndex();
    }

    private boolean greenLine() {
        return GREEN_LINE_NAV_INDEX == getActionBar().getSelectedNavigationIndex();
    }

    private ArrayAdapter<StopInformation> getCurrentAdapter() {
        return redLine() ? mRedAdapter : mGreenAdapter;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        int oldNavIndex = labelToNavIndex();

        if (oldNavIndex != itemPosition) {
            resetUI();
            stopSpinner.setAdapter(getCurrentAdapter());
        }

        mSharedPreferences.edit().putString(getString(R.string.prefDefaultLineKey), navIndexToLabel()).apply();
        return true;
    }

    private String navIndexToLabel() {

        String label = getString(R.string.redLine);

        if (RED_LINE_NAV_INDEX == getActionBar().getSelectedNavigationIndex()) {
            label = getString(R.string.redLine);
        } else if (GREEN_LINE_NAV_INDEX == getActionBar().getSelectedNavigationIndex()){
            label = getString(R.string.greenLine);
        }

        return label;
    }

    private int labelToNavIndex() {

        int index = RED_LINE_NAV_INDEX;

        String currentLabel = mSharedPreferences.getString(getString(R.string.prefDefaultLineKey), getString(R.string.redLine));

        if (getString(R.string.redLine).equals(currentLabel)) {
            index = RED_LINE_NAV_INDEX;
        } else if (getString(R.string.greenLine).equals(currentLabel)) {
            index = GREEN_LINE_NAV_INDEX;
        }

        return index;
    }

    protected void makeInfoDialogue(String error) {
      AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
      } catch (WindowManager.BadTokenException e) {
          //Ln.e("We wanted to show a dialogue but the activity went away.");
      }

    }

    private class ErrorRunnable implements Runnable {

        private String cause;

        ErrorRunnable(Exception e) {
            cause = e.getMessage();

            if (cause == null) {
                cause = "";
            }

        }

        @Override
        public void run() {
            resetUI();
            makeInfoDialogue("Sorry, there was a network problem looking up the stop. This might be solved by trying again.\n\n" + cause);
        }

    }
}