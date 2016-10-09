package com.safenavdetroit.safenavdetroit.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

import butterknife.ButterKnife;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */
public abstract class BaseActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
    }

    @LayoutRes abstract int getLayoutRes();
}
