package com.safenavdetroit.safenavdetroit.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.safenavdetroit.safenavdetroit.R;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public class LoadingActivity extends BaseActivity {

    public static final String I_LAT = "lat";
    public static final String I_LON = "lon";

    public static Intent getIntent(float lat, float lon, Context context){
        Intent intent = new Intent(context, LoadingActivity.class);
        intent.putExtra(I_LAT, lat);
        intent.putExtra(I_LON, lon);

        return intent;
    }

    @Override int getLayoutRes() {
        return R.layout.activity_loading;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
