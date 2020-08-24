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

public class ShopOwnerCurrentBookings extends  BaseActivity1{
    private RecyclerView mRecyclerView;
    private ShopOwnerCurrentBookingsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<ShopOwnerCurrentBookingsCard> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.shop_owner_current_bookings);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Current Bookings");
        createExampleList();
        buildRecyclerView();
    }

    public void createExampleList() {
        orders= new ArrayList<ShopOwnerCurrentBookingsCard>();
        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Tapish","99999999999","12/07/20"));
        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));




    }

    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.shopOwnerCurrentBookingsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new ShopOwnerCurrentBookingsAdapter(orders);

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
