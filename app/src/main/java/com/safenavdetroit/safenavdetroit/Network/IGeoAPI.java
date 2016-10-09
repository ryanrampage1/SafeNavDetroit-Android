package com.safenavdetroit.safenavdetroit.Network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public interface IGeoAPI {
    @GET("arcgis/rest/services/World/GeocodeServer/findAddressCandidates?{query}=&outFields=*&forStorage=false&f=pjson")
    Observable<GsonClasses.Wraper> getPossible(@Path("query") String query);
}
