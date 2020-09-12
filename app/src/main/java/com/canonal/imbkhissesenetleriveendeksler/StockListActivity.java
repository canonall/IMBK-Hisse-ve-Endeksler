package com.canonal.imbkhissesenetleriveendeksler;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.canonal.imbkhissesenetleriveendeksler.fragment.DecreasingFragment;
import com.canonal.imbkhissesenetleriveendeksler.fragment.IncreasingFragment;
import com.canonal.imbkhissesenetleriveendeksler.fragment.StockFragment;
import com.canonal.imbkhissesenetleriveendeksler.fragment.Volume100Fragment;
import com.canonal.imbkhissesenetleriveendeksler.fragment.Volume30Fragment;
import com.canonal.imbkhissesenetleriveendeksler.fragment.Volume50Fragment;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.DeviceInfo;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.HandshakeRespond;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApi;
import com.canonal.imbkhissesenetleriveendeksler.service.StockApiClient;
import com.canonal.imbkhissesenetleriveendeksler.utilty.Base64Decoder;
import com.canonal.imbkhissesenetleriveendeksler.utilty.Constants;
import com.canonal.imbkhissesenetleriveendeksler.utilty.DeviceInfoManager;
import com.google.android.material.navigation.NavigationView;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private DeviceInfo deviceInfo;
    private StockApi stockApi;

    private String decodedAesKey;
    private String decodedAesIv;
    private String handshakeAuthorization;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);
        ButterKnife.bind(this);

        bundle = new Bundle();

        this.deviceInfo = DeviceInfoManager.getDeviceInfo(deviceInfo, this);
        this.stockApi = StockApiClient.getStockApiClient(stockApi);
        sendHandshakeRequest();

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

                    decodedAesKey = Base64Decoder.decoder(handshakeRespond.getAesKey());
                    decodedAesIv = Base64Decoder.decoder(handshakeRespond.getAesIV());
                    handshakeAuthorization = handshakeRespond.getAuthorization();

                    bundle.putString(getString(R.string.aes_key), decodedAesKey);
                    bundle.putString(getString(R.string.aes_iv), decodedAesIv);
                    bundle.putString(getString(R.string.handshake_auth), handshakeAuthorization);

                    //load UI after bundle is ready
                    loadUiElements();

                    Log.d("HANDSHAKE RESPOND", "AES KEY: " + handshakeRespond.getAesKey());
                    Log.d("HANDSHAKE RESPOND", "AESIV: " + handshakeRespond.getAesIV());
                    Log.d("HANDSHAKE RESPOND", "AUTHORIZATION: " + handshakeRespond.getAuthorization());
                    Log.d("HANDSHAKE RESPOND", "LIFETIME: " + handshakeRespond.getLifeTime());
                    Log.d("HANDSHAKE RESPOND", "STATUS: " + Boolean.toString(handshakeRespond.getStatus().getIsSuccess()));
                }


            }

            @Override
            public void onFailure(Call<HandshakeRespond> call, Throwable t) {
                Log.d("Handshake Request", "Handshake Request ERROR: " + t.getMessage());

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_stock_message:

                StockFragment stockFragment = new StockFragment();
                stockFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        stockFragment).commit();
                break;
            case R.id.nav_increasing_message:

                IncreasingFragment increasingFragment = new IncreasingFragment();
                increasingFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        increasingFragment).commit();
                break;
            case R.id.nav_decreasing_message:

                DecreasingFragment decreasingFragment = new DecreasingFragment();
                decreasingFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        decreasingFragment).commit();
                break;
            case R.id.nav_per_volume_30_message:

                Volume30Fragment volume30Fragment = new Volume30Fragment();
                volume30Fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        volume30Fragment).commit();
                break;
            case R.id.nav_per_volume_50_message:

                Volume50Fragment volume50Fragment = new Volume50Fragment();
                volume50Fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        volume50Fragment).commit();
                break;
            case R.id.nav_per_volume_100_message:

                Volume100Fragment volume100Fragment = new Volume100Fragment();
                volume100Fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        volume100Fragment).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // private void getStockApiClient() {
    //     stockApi = StockApiClient.getClient(Constants.BASE_URL);
    // }

    private void setActionBar() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.imbk_stock_and_index));
        }
    }

    private void setToggle() {

        //To get and rotate hamburger icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //open stockFragment first with activity start
        StockFragment stockFragment = new StockFragment();
        stockFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                stockFragment).commit();
        navView.setCheckedItem(R.id.nav_stock_message);
    }

    private void loadUiElements() {
        navView.setNavigationItemSelectedListener(this);
        setActionBar();
        setToggle();
    }

    @Override
    public void onBackPressed() {

        //close navigation drawer instead of activity if it is open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}