package com.canonal.imbkhissesenetleriveendeksler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.canonal.imbkhissesenetleriveendeksler.model.detail.DetailRespond;
import com.canonal.imbkhissesenetleriveendeksler.model.detail.StockId;
import com.canonal.imbkhissesenetleriveendeksler.model.stock.Stock;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApi;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_detail_symbol)
    TextView tvDetailSymbol;
    @BindView(R.id.tv_detail_price)
    TextView tvDetailPrice;
    @BindView(R.id.tv_detail_difference)
    TextView tvDetailDifference;
    @BindView(R.id.tv_detail_volume)
    TextView tvDetailVolume;
    @BindView(R.id.tv_detail_bid)
    TextView tvDetailBid;
    @BindView(R.id.tv_detail_offer)
    TextView tvDetailOffer;
    @BindView(R.id.tv_daily_decrease)
    TextView tvDailyDecrease;
    @BindView(R.id.tv_daily_increase)
    TextView tvDailyIncrease;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_ceil)
    TextView tvCeil;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.tv_fluctuation)
    TextView tvFluctuation;

    private StockApi stockApi;
    private StockId stockId;
    private String handshakeAuth;
    private DetailRespond detailRespond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        ButterKnife.bind(this);

        stockApi = StockApiClient.getStockApiClient(stockApi);

        Stock stock = getIntentData();

        getStockDetail(stock);
    }

    private void getStockDetail(Stock stock) {
        StockId stockId = new StockId();
        stockId.setStockId(stock.getId());
        Call<DetailRespond> call = stockApi.sendStockId(handshakeAuth, stockId);
        call.enqueue(new Callback<DetailRespond>() {
            @Override
            public void onResponse(Call<DetailRespond> call, Response<DetailRespond> response) {

                //Request failed
                if (!response.isSuccessful()) {
                    Log.d("Stock Detail Request", "Stock Detail Request ERROR CODE: " + response.code());
                }

                detailRespond = response.body();

                if (detailRespond != null) {

                    setStockDetailInfoUi(detailRespond);
                }

            }

            @Override
            public void onFailure(Call<DetailRespond> call, Throwable t) {
                Log.d("Stock Detail Request", "Stock Detail Request ERROR CODE: " + t.getMessage());

            }
        });
    }

    private void setStockDetailInfoUi(DetailRespond detailRespond) {

        tvDetailSymbol.append(": " + detailRespond.getSymbol());
        tvDetailPrice.append(": " + detailRespond.getPrice());
        tvDetailDifference.append(": " + detailRespond.getDifference());
        tvDetailVolume.append(": " + detailRespond.getVolume());
        tvDetailBid.append(": " + detailRespond.getBid());
        tvDetailOffer.append(": " + detailRespond.getOffer());
        tvDailyDecrease.append(": " + detailRespond.getLowest());
        tvDailyIncrease.append(": " + detailRespond.getHighest());
        tvNumber.append(": " + detailRespond.getCount());
        tvCeil.append(": " + detailRespond.getMaximum());
        tvBottom.append(": " + detailRespond.getMinimum());
        tvFluctuation.append(": " + detailRespond.getIsDown());
    }

    private Stock getIntentData() {
        Intent intent = getIntent();
        this.handshakeAuth = intent.getParcelableExtra(getString(R.string.handshake_auth));
        return intent.getParcelableExtra(getString(R.string.period_respond));
    }
}