package com.canonal.imbkhissesenetleriveendeksler.fragment;

import android.content.Intent;
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
import com.canonal.imbkhissesenetleriveendeksler.StockDetailActivity;
import com.canonal.imbkhissesenetleriveendeksler.adapter.StockAdapter;
import com.canonal.imbkhissesenetleriveendeksler.model.stock.Period;
import com.canonal.imbkhissesenetleriveendeksler.model.stock.PeriodRespond;
import com.canonal.imbkhissesenetleriveendeksler.service.ListRequest;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApi;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApiClient;
import com.canonal.imbkhissesenetleriveendeksler.utilty.RvInitiator;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DecreasingFragment extends Fragment implements StockAdapter.OnItemClickListener, ListRequest, RvInitiator {

    private static final String plainPeriodText = "decreasing:";

    @BindView(R.id.rv_decreasing)
    RecyclerView rvDecreasing;

    private StockApi stockApi;
    private StockAdapter stockAdapter;

    private Period period;
    private PeriodRespond periodRespond;

    private String aesKey;
    private String aesIv;
    private String handshakeAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_decreasing, container, false);

        stockApi = StockApiClient.getStockApiClient(stockApi);

        if (getArguments() != null) {
            aesKey = getArguments().getString(getString(R.string.aes_key));
            aesIv = getArguments().getString(getString(R.string.aes_iv));
            handshakeAuth = getArguments().getString(getString(R.string.handshake_auth));

            Log.d("DECREASING FRAGMENT", "aes key: " + aesKey);
            Log.d("DECREASING FRAGMENT", "aes iv: " + aesIv);
            Log.d("DECREASING FRAGMENT", "handshake auth: " + handshakeAuth);
        }

        period = new Period();
        sendListRequest();

        return view;
    }

    @Override
    public void sendListRequest() {
        Call<PeriodRespond> call = stockApi.sendPeriod(handshakeAuth, period);
        call.enqueue(new Callback<PeriodRespond>() {
            @Override
            public void onResponse(Call<PeriodRespond> call, Response<PeriodRespond> response) {

                //Request failed
                if (!response.isSuccessful()) {
                    Log.d("Period Request", "Period Request ERROR CODE: " + response.code());
                }

                 periodRespond = response.body();
                Log.d("STOKC PERIOD RESPOND: ", "periodRespond: " + periodRespond.getStatus().getIsSuccess());

                initiateStockRv(periodRespond);
            }

            @Override
            public void onFailure(Call<PeriodRespond> call, Throwable t) {
                Log.d("Period Request", "Period Request ERROR: " + t.getMessage());
            }
        });

    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(getContext(), StockDetailActivity.class);
        intent.putExtra(getString(R.string.period_respond), periodRespond.getStocks().get(position));
        startActivity(intent);
    }

    @Override
    public void initiateStockRv(PeriodRespond periodRespond) {
        rvDecreasing.setLayoutManager(new LinearLayoutManager(getContext()));
        stockAdapter = new StockAdapter(periodRespond, getContext(), this);
        rvDecreasing.setAdapter(stockAdapter);
    }
}
