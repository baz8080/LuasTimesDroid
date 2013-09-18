package com.mbcdev.nextluas.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mbcdev.nextluas.R;
import com.mbcdev.nextluas.constants.StopConstants;
import com.mbcdev.nextluas.location.CriteriaHolder;
import com.mbcdev.nextluas.model.ResultModel;
import com.mbcdev.nextluas.model.ResultModel.StopTime;
import com.mbcdev.nextluas.model.StopInformationModel;
import com.mbcdev.nextluas.net.LocalTranscodeSiteConnector;
import com.mbcdev.nextluas.net.LuasInfoConnector;
import com.mbcdev.nextluas.prefs.MultiSelectListPreference;
import com.mbcdev.nextluas.sorting.DistanceComparator;
import com.mbcdev.nextluas.sorting.StopIndexComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.InjectView;
import butterknife.Views;


public class NextLuasActivity extends AbstractActivity implements OnItemSelectedListener, LocationListener {

    private ConnectivityManager mConnectivityManager;

    private LuasInfoConnector mLuasConnector;

    private LocationManager mLocationManager;

    private ResultModel stopModel;
    private Location currentLocation;

    final Handler lookupHandler = new Handler();

    private ArrayAdapter<StopInformationModel> redAdapter;
    private ArrayAdapter<StopInformationModel> greenAdapter;
    private int currentMapKey;

    private SharedPreferences prefs;

    @InjectView(R.id.disclaimer)
    TextView disclaimer;

    @InjectView(R.id.stopSpinner)
    Spinner stopSpinner;

    @InjectView(R.id.txtLastUpdated)
    TextView txtLastUpdated;
    @InjectView(R.id.txtStopLineInfo)
    TextView txtInfo;

    @InjectView(R.id.tblInbound)
    TableLayout inboundTable;
    @InjectView(R.id.tblOutbound)
    TableLayout outboundTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.luas_ads);

        Views.inject(this);

        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        mLuasConnector = new LocalTranscodeSiteConnector();

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        redAdapter = fromStopModel(StopConstants.getRedStops());
        greenAdapter = fromStopModel(StopConstants.getGreenStops());

        String preferenceKey = prefs.getString(getString(R.string.prefDefaultLineKey), "");

        if (preferenceKey != null && !"".equals(preferenceKey)) {
            if (preferenceKey.equals(getString(R.string.redLine))) {
                currentMapKey = R.string.redLine;
            } else if (preferenceKey.equals(getString(R.string.greenLine))) {
                currentMapKey = R.string.greenLine;
            }
        } else {
            currentMapKey = R.string.redLine;
        }

        currentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        disclaimer.setMovementMethod(LinkMovementMethod.getInstance());

        stopSpinner.setOnItemSelectedListener(this);

        // Work-around for http://code.google.com/p/android/issues/detail?id=7786
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }



        setupSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String provider = mLocationManager.getBestProvider(CriteriaHolder.FINE_CRITERIA, true);

        if (provider != null) {
            mLocationManager.requestLocationUpdates(provider, UPDATE_MS, UPDATE_DISTANCE, this);
        }

        handleFilter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    private void handleFilter() {

        ArrayAdapter<StopInformationModel> currentAdapter = getCurrentAdapter();

        boolean lineFilterEnabled = isLineFilteringEnabled();

        // We want to reset the adapters in any case
        redAdapter = fromStopModel(StopConstants.getRedStops());
        greenAdapter = fromStopModel(StopConstants.getGreenStops());

        if (lineFilterEnabled) {
            String[] filterList = null;

            if (redLine()) {
                filterList = prefs.getString(getString(R.string.prefStopListRedKey), "").split(MultiSelectListPreference.SEPARATOR);
            } else if (greenLine()) {
                filterList = prefs.getString(getString(R.string.prefStopListGreenKey), "").split(MultiSelectListPreference.SEPARATOR);
            }

            if (filterList.length == 0 || (filterList.length == 1 && filterList[0].equals(""))) {
                //Ln.d("Filter enabled but no stops selected, defaulting to all");
            } else {
                List<StopInformationModel> filteredStops = new ArrayList<StopInformationModel>();

                currentAdapter = getCurrentAdapter();

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
        stopSpinner.setSelection(0);
    }

    private boolean isLineFilteringEnabled() {
        return prefs.getBoolean(getString(R.string.prefFilterEnableKey), false);
    }

    private void handleSorting() {
        @SuppressWarnings("unchecked")
        ArrayAdapter<StopInformationModel> adapter = (ArrayAdapter<StopInformationModel>) stopSpinner.getAdapter();

        boolean sortByProximity = prefs.getBoolean(getString(R.string.prefDefaultClosestKey), false);

        StopInformationModel before = (StopInformationModel) stopSpinner.getSelectedItem();

        if (sortByProximity) {
            adapter.sort(new DistanceComparator());
        } else {
            adapter.sort(new StopIndexComparator());
        }

        adapter.notifyDataSetChanged();

        StopInformationModel after = (StopInformationModel) stopSpinner.getSelectedItem();

        /**
         * If the selection has changed then we need to fire off another
         * request, or else we'll be displaying an incorrect set of times.
         */
        if (before != null && !before.equals(after)) {
            lookupStopInfo(after);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuIdChangeLine:
                handleLineSwitch();
                return true;
            case R.id.menuIdHelp:
                launchHelp();
                return true;
            case R.id.menuIdSettings:
                startActivity(new Intent(this, EditPreferenceActivity.class));
                return true;
            case R.id.menuRefresh:
                handleRefreshButton();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * On update of spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (!networkAvailable()) {
            Toast.makeText(this, getString(R.string.noNetwork), Toast.LENGTH_SHORT).show();
            return;
        }

        StopInformationModel model = (StopInformationModel) parent.getItemAtPosition(pos);
        lookupStopInfo(model);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void lookupStopInfo(final StopInformationModel model) {

        txtLastUpdated.setText(getText(R.string.updating));

        Thread t = new Thread() {
            public void run() {
                try {
                    stopModel = mLuasConnector.getStopInfo(model.getSuffix(), model.getDisplayName());

                    lookupHandler.post(new Runnable() {
                        public void run() {
                            updateUI();
                        }
                    });

                } catch (IOException e) {
                    lookupHandler.post(new ErrorRunnable(e));
                }
            }
        };
        t.start();
    }


    /**
     * Update the UI with results from web call
     */
    protected void updateUI() {

        if (stopModel == null || (stopModel.getInbound().isEmpty() && stopModel.getOutbound().isEmpty())) {
            makeInfoDialogue("There was a problem getting the times, perhaps the times on luas.ie are missing?");
            resetUI();
            return;
        }

        txtInfo.setText(currentMapKey);
        txtInfo.setTextColor(redLine() ? Color.RED : Color.GREEN);

        txtLastUpdated.setText(String.format(getString(R.string.lastUpdate), stopModel.getFormattedLastUpdated()));

        inboundTable.removeAllViews();
        addTimesToTable(inboundTable, stopModel.getInbound());

        outboundTable.removeAllViews();
        addTimesToTable(outboundTable, stopModel.getOutbound());
    }

    private boolean networkAvailable() {
        return mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
                || mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
    }

    private void setupSpinner() {
        ArrayAdapter<StopInformationModel> adapter = getCurrentAdapter();
        stopSpinner.setAdapter(adapter);

        updateStopsWithLocation();
        handleSorting();
        handleFilter();
    }

    private void resetUI() {
        inboundTable.removeAllViews();
        outboundTable.removeAllViews();
        txtLastUpdated.setText("");
        txtInfo.setText("");
    }

    private void handleLineSwitch() {
        resetUI();

        if (redLine()) {
            this.currentMapKey = R.string.greenLine;
        } else if (greenLine()) {
            this.currentMapKey = R.string.greenLine;
        } else {
            this.currentMapKey = R.string.redLine;
        }

        setupSpinner();

    }

    private void handleRefreshButton() {
        StopInformationModel model = (StopInformationModel) stopSpinner.getSelectedItem();
        lookupStopInfo(model);
    }

    private void addTimesToTable(TableLayout layout, List<StopTime> stops) {

        int LARGE_FONT = 18;

        for (StopTime st : stops) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            TextView name = new TextView(this);
            name.setLayoutParams(lp);
            name.setText(st.getName());
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, LARGE_FONT);

            TextView minutes = new TextView(this);
            minutes.setGravity(Gravity.RIGHT);
            minutes.setLayoutParams(lp);
            minutes.setText(st.getMinutes());
            minutes.setTextSize(TypedValue.COMPLEX_UNIT_SP, LARGE_FONT);

            tr.addView(name);
            tr.addView(minutes);

            layout.addView(tr, lp);
        }

        layout.setColumnStretchable(0, true);
        layout.setColumnShrinkable(1, true);

    }


    @Override
    public void onLocationChanged(Location location) {
        this.currentLocation = location;
        updateStopsWithLocation();
        handleSorting();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prefs.edit().putString(getString(R.string.prefDefaultLineKey), getString(currentMapKey)).commit();
    }

    private ArrayAdapter<StopInformationModel> fromStopModel(List<StopInformationModel> stopList) {
        ArrayAdapter<StopInformationModel> adapter = new ArrayAdapter<StopInformationModel>(this, android.R.layout.simple_spinner_item);

        for (StopInformationModel model : stopList) {
            adapter.add(model);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void launchHelp() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mbcdev.com/luas-times-help/"));
        startActivity(i);
    }

    private void updateStopsWithLocation() {

        if (this.currentLocation == null) {
            return;
        }

        updateAdapterWithLocation(getCurrentAdapter());
    }

    private void updateAdapterWithLocation(ArrayAdapter<StopInformationModel> adapter) {

        for (int i = 0; i < adapter.getCount(); i++) {
            StopInformationModel model = adapter.getItem(i);

            if (model.getLocation() != null) {
                float[] results = new float[3];
                Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), model.getLocation()
                        .getLatitude(), model.getLocation().getLongitude(), results);

                model.setDistanceFromCurrent(results[0] / 1000);
            }
        }
    }

    private boolean redLine() {
        return R.string.redLine == currentMapKey;
    }

    private boolean greenLine() {
        return R.string.greenLine == currentMapKey;
    }

    private ArrayAdapter<StopInformationModel> getCurrentAdapter() {
        return redLine() ? redAdapter : greenAdapter;
    }

    private class ErrorRunnable implements Runnable {

        private String cause;

        public ErrorRunnable(Exception e) {
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