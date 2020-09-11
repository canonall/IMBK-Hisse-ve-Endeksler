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

    private StockAdapter stockAdapter;

    private String aesKey;
    private String aesIv;
    private String handshakeAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        if (getArguments() != null) {
            aesKey = getArguments().getString(getString(R.string.aes_key));
            aesIv = getArguments().getString(getString(R.string.aes_iv));
            handshakeAuth = getArguments().getString(getString(R.string.handshake_auth));

            Log.d("STOCK FRAGMENT", "aes key: " + aesKey);
            Log.d("STOCK FRAGMENT", "aes key: " + aesIv);
            Log.d("STOCK FRAGMENT", "aes key: " + handshakeAuth);
        }
        return view;
    }

    private void initiateStockRv() {

        rvStock.setLayoutManager(new LinearLayoutManager(getContext()));
        stockAdapter = new StockAdapter(getContext(), this);
        rvStock.setAdapter(stockAdapter);
    }


    @Override
    public void OnItemClick(int position) {
        //TODO on stock clicked
        //TODO detay kısmı
    }
}
