package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ShopOwnerHome extends  AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopowner_home_page);

        ArrayList<ShopOwnerHomeCard> Medicines= new ArrayList<ShopOwnerHomeCard>();
        Medicines.add(new ShopOwnerHomeCard("Paracetamol","150MG","XYZ","available"));
        Medicines.add(new ShopOwnerHomeCard("Dolo","150MG","XYZ","available"));
        Medicines.add(new ShopOwnerHomeCard("Crocin","150MG","XYZ","available"));
        Medicines.add(new ShopOwnerHomeCard("Wikoryl","150MG","XYZ","available"));

        mRecyclerView=findViewById(R.id.shopOwnerHomePageRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout= new LinearLayoutManager(this );
        mAdapter= new ShopOwnerHomeAdapter(Medicines);

        mRecyclerView.setLayoutManager(mLayout );
        mRecyclerView.setAdapter(mAdapter);
    }


}
