package com.example.medconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

public class SearchMedicineActivity extends BaseActivity {

    MaterialSearchView searchView;
    private RecyclerView mRecyclerView;
    private medicineAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MedicineItem> medicineList;
    private ProgressBar spinner;
    private RequestQueue queue;
    private NavigationView nav;
    private MenuItem item;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_search_medicine);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Search Medicine");
        queue= Volley.newRequestQueue(this);
        spinner=findViewById(R.id.progress_loader);
        nav = findViewById(R.id.navigation);
        item = nav.getMenu().getItem(3);
        item.setEnabled(false);
        utils=new Utils();
        utils.autoHideKeyboard(findViewById(android.R.id.content).getRootView(),SearchMedicineActivity.this);

        this.createList();
        this.buildRecycleView();
//
//        SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
//        String latitude = sharedPreferences.getString("LATITUDE", "");
//        String longitude = sharedPreferences.getString("LONGITUDE", "");
//        DistanceCalculator dc = new DistanceCalculator(this,latitude,longitude);
//        Log.d("DistanceCalc", sharedPreferences.getString("SHOPLIST", ""));
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
        this.APICall(s);
    }

    private void createList(){
        this.medicineList = new ArrayList<>();
    }

    private void buildRecycleView(){

        this.mRecyclerView = findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);

        this.mRecyclerViewAdapter = new medicineAdapter(this.medicineList);

        this.mRecyclerViewAdapter = new medicineAdapter(medicineList);

        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mRecyclerViewAdapter);

        this.mRecyclerViewAdapter.setOnItemCLickListener(new medicineAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(SearchMedicineActivity.this, "clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchMedicineActivity.this, SelectShop.class);
                intent.putExtra("id",medicineList.get(position).getId());
//                Intent intent1= new Intent(SearchMedicineActivity.this,MedicineDetails.class);
                intent.putExtra("Medicine",medicineList.get(position));
                startActivity(intent);
            }
        });

    }

    private void APICall(String s){
        spinner.setVisibility(View.VISIBLE);
        String url="https://glacial-caverns-39108.herokuapp.com/medicine/fetch/" + s;

        queue.cancelAll("MedicineList");
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        ArrayList<MedicineItem> filteredList=new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("medicines");
//                                    .getJSONObject("list");
//                            int maxItems = result.getInt("end");
//                            JSONArray resultList = result.getJSONArray("item");
                            //this.medicineList.add(new ShopOwnerSearchMedicineCard("Paracetamol","XYZ","150MG"));

                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject= result.getJSONObject(i);
                                Log.d("JSON Result",jsonObject.getString("name"));
                                filteredList.add(new MedicineItem(jsonObject.getString("_id"),jsonObject.getString("name"),jsonObject.getString("manufacturer"),jsonObject.getString("strength"),jsonObject.getBoolean("prescription")));

                            }

                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(SearchMedicineActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        medicineList=filteredList;

                        spinner.setVisibility(View.GONE);

                        TextView t = findViewById(R.id.searchMedicinePrompt);
                        if(medicineList.size() > 0){

                            t.setVisibility(View.INVISIBLE);
                        }
                        else{
                            t.setVisibility(View.VISIBLE);
                        }

                        mRecyclerViewAdapter.filterList(filteredList);
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(SearchMedicineActivity.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("MedicineList");

        // executing the request (adding to queue)
        queue.add(stringRequest);


    }

}