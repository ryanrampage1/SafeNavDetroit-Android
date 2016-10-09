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

public class SafeNavDetroitAPI {

    public final static SafeNavDetroitAPI INSTANCE = new SafeNavDetroitAPI();

    /**
     * Retrofit network call interface
     */
    private final ISafeNavDetroitAPI apiService;

    private SafeNavDetroitAPI() {

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
//                .baseUrl("http://69.164.221.203:5000/")
                .baseUrl("http://10.0.2.2/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = restAdapter.create(ISafeNavDetroitAPI.class);
    }


    public ISafeNavDetroitAPI getRestAdapter() { return apiService; }
}
