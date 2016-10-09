package com.safenavdetroit.safenavdetroit.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.map.Graphic;
import com.safenavdetroit.safenavdetroit.R;

import butterknife.BindView;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public class MapActivity extends BaseActivity {

    GraphicsLayer mLocationLayer;
    boolean mIsMapLoaded = false;

    @BindView(R.id.map) MapView mMapView;

    public static final String I_LAT = "lat";
    public static final String I_LON = "lon";

    public static Intent getIntent(float lat, float lon, Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(I_LAT, lat);
        intent.putExtra(I_LON, lon);

        return intent;
    }

    @Override int getLayoutRes() {
        return R.layout.activity_map;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationLayer = new GraphicsLayer();
        mMapView.addLayer(mLocationLayer);
        mMapView.setOnStatusChangedListener((OnStatusChangedListener) (source, status) -> {
            if ((source == mMapView) && (status == OnStatusChangedListener.STATUS.INITIALIZED)) {
                mIsMapLoaded = true;
            }
        });

        mMapView.centerAt(getLat(), getLon(), false);
        mLocationLayer.addGraphic(new Graphic())
    }

    private float getLat()  { return getIntent().getFloatExtra(I_LAT, 0); }
    private float getLon()  { return getIntent().getFloatExtra(I_LON, 0); }

}
