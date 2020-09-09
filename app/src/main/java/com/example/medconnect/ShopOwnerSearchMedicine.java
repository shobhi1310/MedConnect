package com.example.medconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopOwnerSearchMedicine extends BaseActivity1 {

    private MenuItem item;
    private NavigationView nav;
    private RecyclerView mRecyclerView;
    private ShopOwnerSearchMedicineAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ShopOwnerSearchMedicineCard> medicineList;
    private RequestQueue queue;
    private ProgressBar spinner;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_search_medicine);

        nav = findViewById(R.id.navigation);
        item = nav.getMenu().getItem(4);
        item.setEnabled(false);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Medicine");
        queue = Volley.newRequestQueue(this);
        spinner = (ProgressBar)findViewById(R.id.progress_loader);


        utils=new Utils();
        utils.autoHideKeyboard(findViewById(android.R.id.content).getRootView(),ShopOwnerSearchMedicine.this);


        this.createList();
        this.buildRecycleView();





        EditText text = findViewById(R.id.searchBox);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String s){
        if(s.length()==0){
            LinearLayout t = findViewById(R.id.tabletprompt);
            t.setVisibility(View.GONE);
            medicineList.clear();
            mRecyclerViewAdapter.notifyDataSetChanged();
            return;
        }

        this.APICall(s);
    }



    private void createList(){
        this.medicineList = new ArrayList<>();
    }

    private void buildRecycleView(){

        this.mRecyclerView = findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);

        this.mRecyclerViewAdapter = new ShopOwnerSearchMedicineAdapter(this.medicineList);

        this.mRecyclerViewAdapter = new ShopOwnerSearchMedicineAdapter(medicineList);

        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mRecyclerViewAdapter);

        this.mRecyclerViewAdapter.setOnItemCLickListener(new ShopOwnerSearchMedicineAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(ShopOwnerSearchMedicine.this, "clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ShopOwnerSearchMedicine.this, ShopOwnerAddMedicine.class);
                intent.putExtra("medicine", medicineList.get(position).getMedicineName());
                intent.putExtra("manufacturer", medicineList.get(position).getManufacturer());
                intent.putExtra("strength", medicineList.get(position).getWeight());
                intent.putExtra("id", medicineList.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void APICall(String s){
        //Avoid overlap with spinner
        LinearLayout t = findViewById(R.id.tabletprompt);
        t.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        String url="https://glacial-caverns-39108.herokuapp.com/medicine/fetch/"+s;

        queue.cancelAll("MedicineList");
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        ArrayList<ShopOwnerSearchMedicineCard> filteredList=new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("medicines");
                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject= result.getJSONObject(i);
                                Log.d("JSON Result",jsonObject.getString("name"));
                                filteredList.add(new ShopOwnerSearchMedicineCard(jsonObject.getString("_id"),jsonObject.getString("name"),jsonObject.getString("manufacturer"),jsonObject.getString("strength")));

                            }

                        } catch (JSONException e) {
                            //Toast.makeText(ShopOwnerSearchMedicine.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        medicineList=filteredList;

                        spinner.setVisibility(View.GONE);


                        LinearLayout t = findViewById(R.id.tabletprompt);
                        if(medicineList.size()>0){
                            t.setVisibility(View.GONE);
                        }
                        else{
                            t.setVisibility(View.VISIBLE);
                        }


                       mRecyclerViewAdapter.filterList(filteredList);
                    } // public void onResponse(String response)
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopOwnerSearchMedicine.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("MedicineList");

        queue.add(stringRequest);

    }

}