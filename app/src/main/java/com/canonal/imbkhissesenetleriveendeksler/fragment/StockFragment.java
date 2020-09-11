package com.canonal.imbkhissesenetleriveendeksler.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.canonal.imbkhissesenetleriveendeksler.R;
import com.canonal.imbkhissesenetleriveendeksler.adapter.StockAdapter;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.DeviceInfo;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.HandshakeRespond;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApi;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApiClient;
import com.canonal.imbkhissesenetleriveendeksler.utilty.Constants;
import com.canonal.imbkhissesenetleriveendeksler.utilty.DeviceInfoManager;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockFragment extends Fragment implements StockAdapter.OnItemClickListener {

    @BindView(R.id.rv_stock)
    RecyclerView rvStock;

    private DeviceInfo deviceInfo;
    private StockApi stockApi;
    private StockAdapter stockAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        this.deviceInfo = DeviceInfoManager.getDeviceInfo(deviceInfo, getContext());

        getStockApiClient();
        sendHandshakeRequest();


        return view;
    }

    private void sendHandshakeRequest() {
        Call<HandshakeRespond> call = stockApi.sendDeviceInfo(deviceInfo);
        call.enqueue(new Callback<HandshakeRespond>() {
            @Override
            public void onResponse(Call<HandshakeRespond> call, Response<HandshakeRespond> response) {

                //Request failed
                if (!response.isSuccessful()) {
                    Log.d("Handshake Request", "Handshake Request ERROR CODE: " + response.code());
                }

                HandshakeRespond handshakeRespond = response.body();

                if (handshakeRespond != null) {

                    Log.d("HANDSHAKE RESPOND", "AES KEY:"+handshakeRespond.getAesKey());
                    Log.d("HANDSHAKE RESPOND", "AESIV"+handshakeRespond.getAesIV());
                    Log.d("HANDSHAKE RESPOND", "AUTHORIZATION"+handshakeRespond.getAuthorization());
                    Log.d("HANDSHAKE RESPOND", "LIFETIME"+handshakeRespond.getLifeTime());
                    Log.d("HANDSHAKE RESPOND", "STATUS: "+Boolean.toString(handshakeRespond.getStatus().getIsSuccess()));
                }


            }

            @Override
            public void onFailure(Call<HandshakeRespond> call, Throwable t) {
                Log.d("Handshake Request", "Handshake Request ERROR: " + t.getMessage());

            }
        });
    }


    private void initiateStockRv() {

        rvStock.setLayoutManager(new LinearLayoutManager(getContext()));
        stockAdapter = new StockAdapter(getContext(), this);
        rvStock.setAdapter(stockAdapter);
    }

    private void getStockApiClient() {
        stockApi = StockApiClient.getClient(Constants.BASE_URL);
    }

    @Override
    public void OnItemClick(int position) {
        //TODO on stock clicked
        //TODO detay kısmı
    }
}
