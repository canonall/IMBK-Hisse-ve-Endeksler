package com.canonal.imbkhissesenetleriveendeksler.service;

import com.canonal.imbkhissesenetleriveendeksler.model.handshake.DeviceInfo;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.HandshakeRespond;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StockApi {

    @POST("api/handshake/start")
    Call<HandshakeRespond> sendDeviceInfo(@Body DeviceInfo deviceInfo);
}
