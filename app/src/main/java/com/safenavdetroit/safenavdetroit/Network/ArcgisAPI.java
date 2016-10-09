package com.safenavdetroit.safenavdetroit.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public class ArcgisAPI {
    public final static ArcgisAPI INSTANCE = new ArcgisAPI();

    /**
     * Retrofit network call interface
     */
    private final IArcgisAPI apiService;

    private ArcgisAPI() {

        // create gson object
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            // Customize the request
            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
//                    .header("Authorization", "")
                    .method(original.method(), original.body())
                    .build();

            // Customize or return the response
            return chain.proceed(request);
        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();


        // create Retrofit rest adapter
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("http://geocode.arcgis.com/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = restAdapter.create(IArcgisAPI.class);
    }

    public IArcgisAPI getRestAdapter() { return apiService; }

}
