package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CustomerHomePage extends BaseActivity {
    private RecyclerView mRecyclerView;
    private CustomerBookingHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<CustomerBookingHistoryCard> orders;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_customer_home_page);
        createExampleList();
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.customerBookingHistoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new CustomerBookingHistoryAdapter(orders);

        mRecyclerView.setLayoutManager(mLayout );
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void createExampleList() {
        orders= new ArrayList<CustomerBookingHistoryCard>();
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Dolo","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));


    }

}