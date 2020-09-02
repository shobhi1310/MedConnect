package com.example.medconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
//        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_home_page);


        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Home");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addInventory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShopOwnerHome.this, ShopOwnerSearchMedicine.class);
                startActivity(i);
            }
        });

        queue = Volley.newRequestQueue(this);

        createList();
        buildRecyclerView();

        EditText text = findViewById(R.id.editTextTextPersonName2);
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

    private void filter(String s) {


//        ArrayList<ShopOwnerHomeCard> filteredList = new ArrayList<>();
//        Log.d("length of array",Integer.toString(filteredList.size()));
//        for(ShopOwnerHomeCard medItem:this.Medicines){
//            if(medItem.getMedicine().toLowerCase().contains(s.toLowerCase())){
//                filteredList.add(medItem);
//            }
//        }
//
//        this.mAdapter.filterList(filteredList);
        this.APICall(s);
    }

    public void createList() {
        this.Medicines = new ArrayList<>();
        String emp = "";
        APICall(emp);
    }

    public void buildRecyclerView() {
//        mRecyclerView=findViewById(R.id.shopOwnerHomePageRecyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mLayout = new LinearLayoutManager(this);
//        mAdapter = new ShopOwnerHomeAdapter(Medicines);
//
//        mRecyclerView.setLayoutManager(mLayout );
//        mRecyclerView.setAdapter(mAdapter);
//
//
//        mAdapter.setOnItemClickListener(new ShopOwnerHomeAdapter.OnItemClickListener() {
//            @Override
//            public void onDeleteClick(int position) {
//                removeItem(position);
//            }
//            @Override
//            public void updateStatus(int position) {
//                changeStatus(position);
//            }
//        });

        this.mRecyclerView = findViewById(R.id.shopOwnerHomePageRecyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayout = new LinearLayoutManager(this);

        this.mAdapter = new ShopOwnerHomeAdapter(this.Medicines);

        this.mAdapter = new ShopOwnerHomeAdapter(this.Medicines);

        this.mRecyclerView.setLayoutManager(this.mLayout);
        this.mRecyclerView.setAdapter(this.mAdapter);

//        this.mAdapter.setOnItemCLickListener((position)->{
////            @Override
////            public void onItemClick(int position) {

////            }
//        });


        this.mAdapter.setOnItemCLickListener(new ShopOwnerHomeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                String id=Medicines.get(position).getId();

                Medicines.remove(position);
                removeMedicineAPI(id);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void updateStatus(int position) {
                if(Medicines.get(position).getStatus()){
                    Medicines.get(position).setStatus(false);

                }else{
                    Medicines.get(position).setStatus(true);

                }
                updateStatusAPI(Medicines.get(position).getId());
                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void onItemClick(int position) {
                Toast.makeText(ShopOwnerHome.this, "clicked", Toast.LENGTH_LONG).show();
                //things need to be changed
                Intent intent = new Intent(ShopOwnerHome.this, ShopOwnerAddMedicine.class);
                intent.putExtra("medicine", Medicines.get(position).getMedicine());
                intent.putExtra("manufacturer", Medicines.get(position).getManufacturer());
                intent.putExtra("strength", Medicines.get(position).getStrength());
                intent.putExtra("id", Medicines.get(position).getId());
                startActivity(intent);
            }
        } );

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

    private void APICall(final String s) {
        String url = "https://glacial-caverns-39108.herokuapp.com/shop/medicinelist/5f47e5ea174464ed81cc5100";

        queue.cancelAll("MedicineList");
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        ArrayList<ShopOwnerHomeCard> filteredList = new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("medicines");
                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject= result.getJSONObject(i);
                                Log.d("JSON Result",jsonObject.toString());
                                JSONObject medicineDetails = jsonObject.getJSONObject("medicine");
                                if(s.length()==0 || medicineDetails.getString("name").toLowerCase().contains(s.toLowerCase())){
                                    filteredList.add(new ShopOwnerHomeCard(medicineDetails.getString("_id"), medicineDetails.getString("name"), medicineDetails.getString("strength"), medicineDetails.getString("manufacturer"), jsonObject.getBoolean("status")));
                                }
                            }
                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(ShopOwnerHome.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Medicines = filteredList;
//                        buildRecyclerView();
                        mAdapter.filterList(filteredList);
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
