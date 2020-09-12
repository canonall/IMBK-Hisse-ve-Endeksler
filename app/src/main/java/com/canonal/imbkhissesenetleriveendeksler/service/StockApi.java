package com.canonal.imbkhissesenetleriveendeksler.service;

import com.canonal.imbkhissesenetleriveendeksler.model.detail.DetailRespond;
import com.canonal.imbkhissesenetleriveendeksler.model.detail.StockId;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.DeviceInfo;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.HandshakeRespond;
import com.canonal.imbkhissesenetleriveendeksler.model.stock.Period;
import com.canonal.imbkhissesenetleriveendeksler.model.stock.PeriodRespond;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StockApi {

    @POST("api/handshake/start")
    Call<HandshakeRespond> sendDeviceInfo(@Body DeviceInfo deviceInfo);

    @POST("api/stocks/list")
    Call<PeriodRespond> sendPeriod(@Header ("X-VP-Authorization") String handshakeAuth, @Body Period period);

    @POST("api/stocks/detail")
    Call<DetailRespond> sendStockId(@Header("X-VP-Authorization") String handshakeAuth, @Body StockId stockId);
}
