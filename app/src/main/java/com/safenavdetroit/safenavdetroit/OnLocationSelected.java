package com.safenavdetroit.safenavdetroit;

import com.esri.core.tasks.geocode.LocatorGeocodeResult;

/**
 * Created by ryancasler on 10/9/16
 * SafeNavDetroit
 */
public interface OnLocationSelected {
    void onLocationSelected(LocatorGeocodeResult location);
}