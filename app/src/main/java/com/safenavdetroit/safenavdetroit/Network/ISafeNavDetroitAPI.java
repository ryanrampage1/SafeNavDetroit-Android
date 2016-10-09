package com.safenavdetroit.safenavdetroit.Network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */
public interface ISafeNavDetroitAPI {
    @GET("/coordinate/{info}")
    Observable<GsonClasses.CoordinateResponse> getCrimes(@Path("info") String info);
}
