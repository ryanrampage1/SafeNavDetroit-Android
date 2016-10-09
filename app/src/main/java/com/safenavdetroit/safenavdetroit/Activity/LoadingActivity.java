package com.safenavdetroit.safenavdetroit.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.safenavdetroit.safenavdetroit.Network.SafeNavDetroitAPI;
import com.safenavdetroit.safenavdetroit.R;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public class LoadingActivity extends BaseActivity {

    public static final String I_LAT = "lat";
    public static final String I_LON = "lon";

    public static Intent getIntent(float lat, float lon, Context context) {
        Intent intent = new Intent(context, LoadingActivity.class);
        intent.putExtra(I_LAT, lat);
        intent.putExtra(I_LON, lon);

        return intent;
    }

    @Override
    int getLayoutRes() {
        return R.layout.activity_loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        float lat = getIntent().getFloatExtra(I_LAT, 0);
        float lon = getIntent().getFloatExtra(I_LON, 0);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location current = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        String query = String.valueOf(current.getLatitude()) + "," + String.valueOf(current.getLongitude()) +
                ";" + String.valueOf(lat) + "," + String.valueOf(lon);

        SafeNavDetroitAPI.INSTANCE.getRestAdapter().getCrimes(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coordinateResponse -> Log.d("d", coordinateResponse.toString()),
                        throwable -> Log.e("E", throwable.toString()));

    }
}
