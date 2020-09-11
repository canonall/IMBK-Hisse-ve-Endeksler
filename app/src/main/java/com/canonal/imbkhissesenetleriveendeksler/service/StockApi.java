package com.canonal.imbkhissesenetleriveendeksler.service;

import com.canonal.imbkhissesenetleriveendeksler.model.handshake.DeviceInfo;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.HandshakeRespond;
import com.canonal.imbkhissesenetleriveendeksler.model.stock.StockRespond;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StockApi {

    @POST("api/handshake/start")
    Call<HandshakeRespond> sendDeviceInfo(@Body DeviceInfo deviceInfo);

    @POST("api/stocks/list")
    Call<StockRespond> sendPeriod();
}
