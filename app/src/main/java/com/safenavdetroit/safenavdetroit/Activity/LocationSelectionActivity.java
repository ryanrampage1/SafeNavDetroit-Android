package com.safenavdetroit.safenavdetroit.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.esri.core.tasks.geocode.Locator;
import com.esri.core.tasks.geocode.LocatorFindParameters;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.safenavdetroit.safenavdetroit.LocationAdapter;
import com.safenavdetroit.safenavdetroit.OnLocationSelected;
import com.safenavdetroit.safenavdetroit.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */
public class LocationSelectionActivity extends BaseActivity implements OnLocationSelected {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.edit_text_location) EditText locationEditText;

    private Subscription sourceSubscription;
    private LocationAdapter adapter;
    Locator locator;

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

        locator = Locator.createOnlineLocator();
    }

    @Override protected void onResume() {
        super.onResume();
        setUpFields();
    }

    private void setUpFields() {
        sourceSubscription = RxTextView.
                textChanges(locationEditText)
                .skip(1)
                .debounce(333, TimeUnit.MILLISECONDS)
                .map(charSequence -> {

                    //locator.find() throws exception so we have to catch it
                    try {
                        return locator.find(new LocatorFindParameters(charSequence.toString()));
                    } catch (Exception e) {
                        Log.e("Get locations", e.toString());
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locatorGeocodeResults -> {
                            Log.d("List", locatorGeocodeResults.toString());
                            adapter.updateList(locatorGeocodeResults);
                        },
                        throwable -> {
                            Log.e("Get locations", throwable.toString());
                            Snackbar.make(recyclerView, getString(R.string.addressSearchFailed), Snackbar.LENGTH_SHORT).show();
                        });
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


