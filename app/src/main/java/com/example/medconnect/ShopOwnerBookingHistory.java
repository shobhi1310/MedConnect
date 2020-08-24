package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopOwnerBookingHistory extends  BaseActivity1{
    private RecyclerView mRecyclerView;
    private ShopOwnerBookingHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<ShopOwnerBookingHistoryCard> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_booking_history);

        createExampleList();
        buildRecyclerView();
    }

    public void createExampleList() {
        orders= new ArrayList<ShopOwnerBookingHistoryCard>();
        orders.add(new ShopOwnerBookingHistoryCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        orders.add(new ShopOwnerBookingHistoryCard("Paracetamol","150MG","XYZ","Tapish","99999999999","12/07/20"));
        orders.add(new ShopOwnerBookingHistoryCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        orders.add(new ShopOwnerBookingHistoryCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        orders.add(new ShopOwnerBookingHistoryCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));




    }

    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.shopOwnerBookingHistoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new ShopOwnerBookingHistoryAdapter(orders);

        mRecyclerView.setLayoutManager(mLayout );
        mRecyclerView.setAdapter(mAdapter);

    }


//    public void setButtons() {
//
//        buttonRemove = findViewById(R.id.button_remove);
//        buttonRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = Integer.parseInt(editTextRemove.getText().toString());
//                removeItem(position);
//            }
//        });
//    }

}
