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

public class CustomerBookingHistory extends  BaseActivity{
    private RecyclerView mRecyclerView;
    private CustomerBookingHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<CustomerBookingHistoryCard> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_customer_booking_history);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Booking History");

        createExampleList();
        buildRecyclerView();
    }

    public void createExampleList() {
        orders= new ArrayList<CustomerBookingHistoryCard>();
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Dolo","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));
        orders.add(new CustomerBookingHistoryCard("Paracetamol","150MG","XYZ","Apollo Pharmacy","Hyderabad","99999999999"));


    }

    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.customerBookingHistoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new CustomerBookingHistoryAdapter(orders);

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
