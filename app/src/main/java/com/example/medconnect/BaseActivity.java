package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import static com.example.medconnect.GetUserLocation.MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FusedLocationProviderClient fusedLocationClient;
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
                    intent = new Intent(this, CustomerProfile.class);
                    break;
                case R.id.Home:
                    intent = new Intent(this, CustomerHomePage.class);
                    break;
                case R.id.BookingHistory:
                    intent=new Intent(this,CustomerBookingHistory.class);
                    break;
                case R.id.searchMedicine:
                    intent = new Intent(this, SearchMedicineActivity.class);
                    break;
                case R.id.detectMyLocation:
                    intent = new Intent(this,CurrentLocationActivity.class);
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