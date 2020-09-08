package com.example.medconnect;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;

public class ShopOwnerHome extends  BaseActivity1{
    private RecyclerView mRecyclerView;
    private ShopOwnerHomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<ShopOwnerHomeCard> Medicines;
    private ArrayList<ShopOwnerHomeCard> originalMedicineList=new ArrayList<>();
    private RequestQueue queue;
    private ProgressBar spinner;
    private String shopOwnerID;
    private String oldString="";

    Utils utils;
    private MenuItem item;
    private NavigationView nav;
    public static final String Data = "StoredData";
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_home_page);

        nav = findViewById(R.id.navigation);
        item = nav.getMenu().getItem(1);
        item.setEnabled(false);


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

        spinner = (ProgressBar)findViewById(R.id.progress_loader);
        spinner.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences= getSharedPreferences(Data,MODE_PRIVATE);
        shopOwnerID=sharedPreferences.getString("ID","");
        Log.d("URL",shopOwnerID);

        createList();
        buildRecyclerView();

//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                shuffle();
//                swipe.setRefreshing(false);
//            }
//        });

        EditText text = findViewById(R.id.editTextTextPersonName2);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==0){
                    Log.d("Search","Zero Length");
                    Medicines.clear();
                    Medicines.addAll(originalMedicineList);
                    LinearLayout linearLayout=findViewById(R.id.tabletprompt);
                    if(Medicines.size()==0){
                        linearLayout.setVisibility(View.VISIBLE);
                    }else{
                        linearLayout.setVisibility(View.GONE);
                    }
                    mAdapter.notifyDataSetChanged();
                    oldString="";
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String s) {
        Log.d("Search","Filter Length");

        ArrayList<ShopOwnerHomeCard> filteredList = new ArrayList<>();

        if(s.length()==0){
            Log.d("Search","Zero Length");
            return;
        }
         if(oldString.length()-s.length()>=1){
            Log.d("Search","Negative Length");
            for(ShopOwnerHomeCard medItem:this.originalMedicineList){
                if(medItem.getMedicine().toLowerCase().indexOf(s.toLowerCase())==0){
                    filteredList.add(medItem);
                }
            }
            oldString=s;
            Medicines.clear();
            Medicines.addAll(filteredList);
            LinearLayout linearLayout=findViewById(R.id.tabletprompt);
            if(Medicines.size()==0){
                linearLayout.setVisibility(View.VISIBLE);
            }else{
                linearLayout.setVisibility(View.GONE);
            }
            mAdapter.notifyDataSetChanged();

        }
        else{
            Log.d("Search","Pos Length");
            for(ShopOwnerHomeCard medItem:this.Medicines){
                if(medItem.getMedicine().toLowerCase().indexOf(s.toLowerCase())==0){
                    filteredList.add(medItem);
                }
            }

            oldString=s;
            Medicines.clear();
            Medicines.addAll(filteredList);
            LinearLayout linearLayout=findViewById(R.id.tabletprompt);
            if(Medicines.size()==0){
                linearLayout.setVisibility(View.VISIBLE);
            }else{
                linearLayout.setVisibility(View.GONE);
            }
            mAdapter.notifyDataSetChanged();
        }

    }

    public void createList() {
        this.Medicines = new ArrayList<>();

        APICall();
    }

    public void buildRecyclerView() {
        LinearLayout linearLayout=findViewById(R.id.tabletprompt);


        this.mRecyclerView = findViewById(R.id.shopOwnerHomePageRecyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayout = new LinearLayoutManager(this);
        this.mAdapter = new ShopOwnerHomeAdapter(this.Medicines);
        this.mAdapter = new ShopOwnerHomeAdapter(this.Medicines);
        this.mRecyclerView.setLayoutManager(this.mLayout);
        this.mRecyclerView.setAdapter(this.mAdapter);




        this.mAdapter.setOnItemCLickListener(new ShopOwnerHomeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                String id=Medicines.get(position).getId();
                Medicines.remove(position);
                for(int i=0;i<originalMedicineList.size();i++){
                    if(originalMedicineList.get(i).getId().equals(id)){
                        originalMedicineList.remove(i);
                        break;
                    }
                }
                removeMedicineAPI(id);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void updateStatus(int position) {

                String id=Medicines.get(position).getId();
                Log.d("Update", Medicines.get(position).getMedicine());
                if(Medicines.get(position).getStatus()){
                    Medicines.get(position).setStatus(false);
                }else{
                    Medicines.get(position).setStatus(true);
                }




                mAdapter.notifyItemChanged(position);
                updateStatusAPI(id);


            }
        });

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

    private void APICall() {
        String url = "https://glacial-caverns-39108.herokuapp.com/shop/medicinelist/"+shopOwnerID;


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

                                filteredList.add(new ShopOwnerHomeCard(medicineDetails.getString("_id"), medicineDetails.getString("name"), medicineDetails.getString("strength"), medicineDetails.getString("manufacturer"), jsonObject.getBoolean("status")));

                            }
                            spinner.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            Toast.makeText(ShopOwnerHome.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Medicines = filteredList;
                        LinearLayout linearLayout=findViewById(R.id.tabletprompt);
                        if(Medicines.size()==0){
                            linearLayout.setVisibility(View.VISIBLE);
                        }else{
                            linearLayout.setVisibility(View.GONE);
                        }

                        originalMedicineList.clear();
                        originalMedicineList.addAll(filteredList);
                        mAdapter.filterList(filteredList);
                    } // public void onResponse(String response)
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopOwnerHome.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("MedicineList");

        // executing the request (adding to queue)
        queue.add(stringRequest);
    }

    private void updateStatusAPI(final String id){
        String url="https://glacial-caverns-39108.herokuapp.com/shop/"+ shopOwnerID +"/update/"+id;
        queue.cancelAll("Update Status");
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                            Log.d("update",response);

                           // Toast.makeText(ShopOwnerHome.this, "Updated", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopOwnerHome.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });

        stringRequest.setTag("Update Status");

        queue.add(stringRequest);

    }


    private void removeMedicineAPI(String id){
        String url="https://glacial-caverns-39108.herokuapp.com/shop/"+shopOwnerID+"/remove/"+id;

        queue.cancelAll("Remove Medicine");
        StringRequest stringRequest= new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ShopOwnerHome.this, "Removed", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error",error.toString());
                        Toast.makeText(ShopOwnerHome.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("Remove Medicine");

        queue.add(stringRequest);

    }

//    private void shuffle() {
//        queue= Volley.newRequestQueue(this);
//        createList();
//        buildRecyclerView();
//        oldString="";
//    }

}
