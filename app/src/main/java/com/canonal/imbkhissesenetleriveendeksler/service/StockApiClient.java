package com.canonal.imbkhissesenetleriveendeksler.service;

import com.canonal.imbkhissesenetleriveendeksler.utilty.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockApiClient {

    private static StockApi getClient() {

        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        return retrofit.create(StockApi.class);
    }

    public static StockApi getStockApiClient(StockApi stockApi) {
        return stockApi = StockApiClient.getClient();

    }
}
