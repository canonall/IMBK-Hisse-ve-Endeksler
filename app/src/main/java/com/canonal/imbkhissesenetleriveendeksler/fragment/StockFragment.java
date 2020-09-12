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
import com.canonal.imbkhissesenetleriveendeksler.model.stock.Stock;
import com.canonal.imbkhissesenetleriveendeksler.service.ListRequest;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApi;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApiClient;
import com.canonal.imbkhissesenetleriveendeksler.utilty.RvInitiator;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockFragment extends Fragment implements StockAdapter.OnItemClickListener, ListRequest, RvInitiator {

    private static final String plainPeriodText = "all";

    @BindView(R.id.rv_stock)
    RecyclerView rvStock;

    private Period period;
    private PeriodRespond periodRespond;

    private StockAdapter stockAdapter;
    private StockApi stockApi;

    private String aesKey;
    private String aesIv;
    private String handshakeAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        stockApi = StockApiClient.getStockApiClient(stockApi);

        if (getArguments() != null) {
            aesKey = getArguments().getString(getString(R.string.aes_key));
            aesIv = getArguments().getString(getString(R.string.aes_iv));
            handshakeAuth = getArguments().getString(getString(R.string.handshake_auth));

            Log.d("STOCK FRAGMENT", "aes key: " + aesKey);
            Log.d("STOCK FRAGMENT", "aes iv: " + aesIv);
            Log.d("STOCK FRAGMENT", "handshake auth: " + handshakeAuth);
        }


        try {

            //TODO
            //Encryption with AES
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7PADDING");
            SecretKey secretKey = new SecretKeySpec(aesKey.getBytes(), 0, aesKey.length(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plainPeriodText.getBytes());
            byte[] iv = cipher.getIV();

            //TODO encrypt period with AES
            //TODO then set it as period
            //TODO thereafter send request
            period = new Period();
            period.setPeriod(Arrays.toString(cipherText));
            sendListRequest();

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

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
                Log.d("STOCK PERIOD RESPOND: ", "periodRespond: " + periodRespond.getStatus().getIsSuccess());


                initiateStockRv(periodRespond);

            }

            @Override
            public void onFailure(Call<PeriodRespond> call, Throwable t) {
                Log.d("Period Request", "Period Request ERROR: " + t.getMessage());
            }
        });
    }

    @Override
    public void initiateStockRv(PeriodRespond periodRespond) {

        rvStock.setLayoutManager(new LinearLayoutManager(getContext()));
        stockAdapter = new StockAdapter(periodRespond, getContext(), this);
        rvStock.setAdapter(stockAdapter);
    }


    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(getContext(), StockDetailActivity.class);
        intent.putExtra(getString(R.string.handshake_auth), handshakeAuth);
        intent.putExtra(getString(R.string.period_respond), periodRespond.getStocks().get(position));
        startActivity(intent);
    }

}
