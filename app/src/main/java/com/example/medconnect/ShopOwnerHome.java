package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopOwnerHome extends  BaseActivity1{
    private RecyclerView mRecyclerView;
    private ShopOwnerHomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<ShopOwnerHomeCard> Medicines;
    private RequestQueue queue;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_home_page);



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

        queue= Volley.newRequestQueue(this);

        createList();

    }

    public void createList() {
        APICall();
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
        String id=Medicines.get(position).getId();

        Medicines.remove(position);
        removeMedicineAPI(id);
        mAdapter.notifyItemRemoved(position);
    }
    public void changeStatus(int position) {

        if(Medicines.get(position).getStatus()){
            Medicines.get(position).setStatus(false);

        }else{
            Medicines.get(position).setStatus(true);

        }
        updateStatusAPI(Medicines.get(position).getId());
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

    private void APICall(){
        String url="https://glacial-caverns-39108.herokuapp.com/shop/medicinelist/5f47e5ea174464ed81cc5100";

        queue.cancelAll("MedicineList");
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        ArrayList<ShopOwnerHomeCard> filteredList=new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("medicines");



                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject= result.getJSONObject(i);
                                Log.d("JSON Result",jsonObject.toString());
                                JSONObject medicineDetails= jsonObject.getJSONObject("medicine");

                                filteredList.add(new ShopOwnerHomeCard(medicineDetails.getString("_id"),medicineDetails.getString("name"),medicineDetails.getString("strength"),medicineDetails.getString("manufacturer"),jsonObject.getBoolean("status")));

                            }





                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(ShopOwnerHome.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Medicines=filteredList;
                        buildRecyclerView();

                    } // public void onResponse(String response)
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(ShopOwnerHome.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("MedicineList");

        // executing the request (adding to queue)
        queue.add(stringRequest);
    }

    private void updateStatusAPI(String id){

        String url="https://glacial-caverns-39108.herokuapp.com/shop/5f47e5ea174464ed81cc5100/update/"+id;

        queue.cancelAll("Update Status");
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {


                            Toast.makeText(ShopOwnerHome.this, "Updated", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(ShopOwnerHome.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("Update Status");

        // executing the request (adding to queue)
        queue.add(stringRequest);

    }


    private void removeMedicineAPI(String id){

        String url="https://glacial-caverns-39108.herokuapp.com/shop/5f47e5ea174464ed81cc5100/remove/"+id;
        //String url="https://localhost:5000/shop/5f47e5ea174464ed81cc5100/remove/"+id;



        queue.cancelAll("Remove Medicine");
        StringRequest stringRequest= new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(ShopOwnerHome.this, "Removed", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Log.e("API Error",error.toString());
                        Toast.makeText(ShopOwnerHome.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("Remove Medicine");

        // executing the request (adding to queue)
        queue.add(stringRequest);

    }



}
