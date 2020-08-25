package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShopOwnerHome extends  BaseActivity1{
    private RecyclerView mRecyclerView;
    private ShopOwnerHomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<ShopOwnerHomeCard> Medicines;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_home_page);
//        setContentView(R.layout.activity_shopowner_home_page);
//        getSupportActionBar().hide();
        //sideNavbar functionality

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Home");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addInventory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShopOwnerHome.this,ShopOwnerSearchMedicine.class);
                startActivity(i);
            }
        });

        createExampleList();
        buildRecyclerView();
    }

    public void createExampleList() {
        Medicines= new ArrayList<ShopOwnerHomeCard>();
        Medicines.add(new ShopOwnerHomeCard("Paracetamol","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Dolo","150MG","XYZ",false));
        Medicines.add(new ShopOwnerHomeCard("Crocin","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Wikoryl","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Paracetamol","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Dolo","150MG","XYZ",false));
        Medicines.add(new ShopOwnerHomeCard("Crocin","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Wikoryl","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Paracetamol","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Dolo","150MG","XYZ",false));
        Medicines.add(new ShopOwnerHomeCard("Crocin","150MG","XYZ",true));
        Medicines.add(new ShopOwnerHomeCard("Wikoryl","150MG","XYZ",true));
    }

    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.shopOwnerHomePageRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new ShopOwnerHomeAdapter(Medicines);

        mRecyclerView.setLayoutManager(mLayout );
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ShopOwnerHomeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
            @Override
            public void updateStatus(int position) {
                changeStatus(position);
            }
        });
    }

    public void removeItem(int position) {
        Medicines.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public void changeStatus(int position) {

        if(Medicines.get(position).getStatus()){
            Medicines.get(position).setStatus(false);

        }else{
            Medicines.get(position).setStatus(true);

        }
        mAdapter.notifyItemChanged(position);
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
