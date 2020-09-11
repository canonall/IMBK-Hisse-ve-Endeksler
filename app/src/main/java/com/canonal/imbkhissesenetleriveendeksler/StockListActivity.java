package com.canonal.imbkhissesenetleriveendeksler;

import android.os.Bundle;
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
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);
        ButterKnife.bind(this);

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new StockFragment()).commit();
        navView.setCheckedItem(R.id.nav_stock_message);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_stock_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StockFragment()).commit();
                break;
            case R.id.nav_increasing_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new IncreasingFragment()).commit();
                break;
            case R.id.nav_decreasing_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DecreasingFragment()).commit();
                break;
            case R.id.nav_per_volume_30_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Volume30Fragment()).commit();
                break;
            case R.id.nav_per_volume_50_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Volume50Fragment()).commit();
                break;
            case R.id.nav_per_volume_100_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Volume100Fragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}