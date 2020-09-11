package com.canonal.imbkhissesenetleriveendeksler.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockApiClient {

    public static StockApi getClient(String baseUrl) {

        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        return retrofit.create(StockApi.class);
    }
}
