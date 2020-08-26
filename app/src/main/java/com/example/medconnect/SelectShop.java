package com.example.medconnect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectShop extends  AppCompatActivity{
    private RecyclerView mRecyclerView;
    private SelectShopAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<SelectShopCard> shops;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_title.setText("Select Shop");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        createExampleList();
        buildRecyclerView();
    }

    public void createExampleList() {
        shops= new ArrayList<SelectShopCard>();
        shops.add(new SelectShopCard("Apollo Pharmacy","Hyderabad","99999999999","1.5 km"));
        shops.add(new SelectShopCard("Sameed Pharmacy","Hyderabad","99999999999","1.5 km"));
        shops.add(new SelectShopCard("Tapish Pharmacy","Hyderabad","99999999999","1.5 km"));
        shops.add(new SelectShopCard("Shubh Pharmacy","Hyderabad","99999999999","1.5 km"));
        shops.add(new SelectShopCard("Apollo Pharmacy","Hyderabad","99999999999","1.5 km"));
        shops.add(new SelectShopCard("Apollo Pharmacy","Hyderabad","99999999999","1.5 km"));




    }

    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.selectShopRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new SelectShopAdapter(shops);

        mRecyclerView.setLayoutManager(mLayout );
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SelectShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SelectShop.this, MedicineDetails.class);
                startActivity(intent);
            }



        });



//
//        mRecyclerView.setOnItemCLickListener(new medicineAdapter.OnItemCLickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Toast.makeText(SearchMedicineActivity.this, "clicked", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(SearchMedicineActivity.this, SelectShop.class);
//                startActivity(intent);
//            }
//        });

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
