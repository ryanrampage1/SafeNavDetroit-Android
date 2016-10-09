package com.safenavdetroit.safenavdetroit.Network;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public interface IArcgisAPI {

    @FormUrlEncoded
    @POST("sharing/rest/oauth2/token/")
    Observable<GsonClasses.OauthResponse> login(@Field("client_id") String cid, @Field("client_secret") String cs, @Field("grant_type") String gt);

}
