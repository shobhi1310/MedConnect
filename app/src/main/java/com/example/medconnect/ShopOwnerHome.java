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

public class ShopOwnerHome extends  BaseActivity1{
    private RecyclerView mRecyclerView;
    private ShopOwnerHomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<ShopOwnerHomeCard> Medicines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_home_page);
//        setContentView(R.layout.activity_shopowner_home_page);
//        getSupportActionBar().hide();
        //sideNavbar functionality
        final DrawerLayout drawerLayout = findViewById(R.id.sideNavbar);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Home");

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
