package com.safenavdetroit.safenavdetroit.Activity;

import android.os.Bundle;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnStatusChangedListener;
import com.safenavdetroit.safenavdetroit.R;

import butterknife.BindView;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public class MainActivity extends BaseActivity {

    GraphicsLayer mLocationLayer;
    boolean mIsMapLoaded = false;
    @BindView(R.id.map) MapView mMapView;

    @Override int getLayoutRes() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationLayer = new GraphicsLayer();
        mMapView.addLayer(mLocationLayer);

        mMapView.setOnStatusChangedListener((OnStatusChangedListener) (source, status) -> {
            if ((source == mMapView) && (status == OnStatusChangedListener.STATUS.INITIALIZED)) {
                mIsMapLoaded = true;
            }
        });
    }

}
