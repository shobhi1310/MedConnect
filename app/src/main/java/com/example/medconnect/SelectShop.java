package com.example.medconnect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectShop extends  AppCompatActivity{
    private RecyclerView mRecyclerView;
    private SelectShopAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<SelectShopCard> shops;
    private RequestQueue queue;
    private ProgressBar spinner;
    public static final String Data = "StoredData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_title.setText("Select Shop");

        spinner=findViewById(R.id.progress_loader);

        queue= Volley.newRequestQueue(this);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        createExampleList();

    }

    public void createExampleList() {
        shops= new ArrayList<SelectShopCard>();
        Intent intent=getIntent();


        this.APICall(intent.getStringExtra("id"));
        Log.d("Array ShopList",shops.toString());



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
//                intent.putExtra("ShopName",);
                intent.putExtra("Shop",shops.get(position));
                MedicineItem medicineItem=null;
                
                if(getIntent().getExtras()!=null) {
                    medicineItem=(MedicineItem) getIntent().getSerializableExtra("Medicine");
                }else{
                    Log.d("Shop","Not Found");
                }
                intent.putExtra("Medicine",medicineItem);
                startActivity(intent);
            }



        });


//        mRecyclerView.setOnItemCLickListener(new medicineAdapter.OnItemCLickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Toast.makeText(SearchMedicineActivity.this, "clicked", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(SearchMedicineActivity.this, SelectShop.class);
//                startActivity(intent);
//            }
//        });

    }

    private void APICall(String id){
        spinner.setVisibility(View.VISIBLE);
        String url="https://glacial-caverns-39108.herokuapp.com/medicine/shoplist/"+id;

        queue.cancelAll("ShopList");
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        ArrayList<SelectShopCard> filteredList=new ArrayList<>();
                        try {
                            Log.d("shops result",response);
                            JSONArray result = new JSONObject(response).getJSONArray("shops");
                            JSONArray tally = extractor();
//                                    .getJSONObject("list");
//                            int maxItems = result.getInt("end");
//                            JSONArray resultList = result.getJSONArray("item");
                            //this.medicineList.add(new ShopOwnerSearchMedicineCard("Paracetamol","XYZ","150MG"));
                        for(int i=0;i<tally.length();i++) {
                            JSONObject values = tally.getJSONObject(i);
                            String comparator_id = values.getString("_id");
                            for (int j = 0; j < result.length(); j++) {
                                JSONObject jsonObject = result.getJSONObject(j);
                                if(comparator_id.equals(jsonObject.getString("_id"))) {
                                    JSONArray coordinates = jsonObject.getJSONArray("location");
                                    filteredList.add(new SelectShopCard(jsonObject.getString("_id"), jsonObject.getString("name"), jsonObject.getString("address"), jsonObject.getString("phone"), values.getString("travelDistance")+"km", coordinates.getDouble(0), coordinates.getDouble(1)));
                                }
                            }
                        }
                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(SelectShop.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        shops=filteredList;
                        Log.d("Array Shop",shops.toString());
                        spinner.setVisibility(View.GONE);
                        buildRecyclerView();

                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(SelectShop.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });

        stringRequest.setTag("ShopList");
        // executing the request (adding to queue)
        queue.add(stringRequest);


    }

    public JSONArray extractor(){
        SharedPreferences sp = getSharedPreferences(Data,MODE_PRIVATE);
        String response = sp.getString("SHOPLIST","");
        //Log.d("Extractor res",response);
        String converted = "{shopLists:"+response+"}";
        JSONArray result = null;
        try {
            result = new JSONObject(converted).getJSONArray("shopLists");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d("Extractor",result.toString());
        return result;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
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
