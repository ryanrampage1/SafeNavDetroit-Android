package com.safenavdetroit.safenavdetroit.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.esri.core.tasks.geocode.Locator;
import com.esri.core.tasks.geocode.LocatorFindParameters;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.safenavdetroit.safenavdetroit.LocationAdapter;
import com.safenavdetroit.safenavdetroit.OnLocationSelected;
import com.safenavdetroit.safenavdetroit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */
public class LocationSelectionActivity extends BaseActivity implements OnLocationSelected {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.edit_text_location) EditText locationEditText;

    private Subscription sourceSubscription;
    private LocationAdapter adapter;

    @Override int getLayoutRes() {
        return R.layout.activity_location_selection;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new LocationAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override protected void onResume() {
        super.onResume();
        setUpFields();
    }

    private void setUpFields() {
        Observable<CharSequence> source = RxTextView.textChanges(locationEditText).skip(1);
        sourceSubscription = source
                .debounce(333, TimeUnit.MILLISECONDS)
                .subscribe(text -> {
                    String s = text.toString();
                    Log.d("d", s);
                    executeLocatorTask(s);
                });
    }

    private void executeLocatorTask(String address) {
        // Create Locator parameters from single line address string
        LocatorFindParameters findParams = new LocatorFindParameters(address);
        new LocatorAsyncTask().execute(findParams);
    }

    private class LocatorAsyncTask extends AsyncTask<LocatorFindParameters, Void, List<LocatorGeocodeResult>> {
        private Exception mException;

        @Override protected List<LocatorGeocodeResult> doInBackground(LocatorFindParameters... params) {
            mException = null;
            List<LocatorGeocodeResult> results = null;
            Locator locator = Locator.createOnlineLocator();
            try {
                results = locator.find(params[0]);
            } catch (Exception e) {
                mException = e;
            }
            return results;
        }

        @Override protected void onPostExecute(List<LocatorGeocodeResult> result) {
            if (mException != null) {
                Log.w("PlaceSearch", "LocatorSyncTask failed with:");
                mException.printStackTrace();
                Toast.makeText(LocationSelectionActivity.this, getString(R.string.addressSearchFailed), Toast.LENGTH_LONG).show();
                return;
            }

            if (result.size() == 0) {
                Toast.makeText(LocationSelectionActivity.this, getString(R.string.noResultsFound), Toast.LENGTH_LONG).show();
            } else {
                Log.d("List", result.toString());
                adapter.updateList(result);
            }
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (sourceSubscription != null)
            sourceSubscription.unsubscribe();
    }

    @Override public void onLocationSelected(LocatorGeocodeResult location) {
        Log.d("Location Selected", location.toString());

        float lon = (float) location.getLocation().getX();
        float lat = (float) location.getLocation().getY();

        startActivity(LoadingActivity.getIntent(lat,lon,this));
    }

}


