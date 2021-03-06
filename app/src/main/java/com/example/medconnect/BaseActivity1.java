package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String Data = "StoredData";

    protected void onCreate(Bundle savedInstanceState,int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.sideNavbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {

            int id = item.getItemId();
            Fragment fragment = null;
            Intent intent = null;

            switch (id) {
                case R.id.menuProfile:
                    intent = new Intent(this, ShopKeeperProfile.class);
                    break;
                case R.id.Home:
                    intent = new Intent(this,ShopOwnerHome.class);
                    break;
                case R.id.BookingHistory:
                    intent = new Intent (this,ShopOwnerBookingHistory.class);
                    break;
                case R.id.currentBooking:
                    intent = new Intent (this,ShopOwnerCurrentBookings.class);
                    break;
                case R.id.addMedicine:
                    intent = new Intent (this,ShopOwnerSearchMedicine.class);
                    break;
                case R.id.logout:
                    Toast.makeText(getApplicationContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    break;
                case R.id.contact:
                    intent = new Intent(this,ContactUsShop.class);
                    break;
            }
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.sideNavbar);
            drawer.closeDrawer(GravityCompat.START);
        }catch (Error e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.sideNavbar);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}