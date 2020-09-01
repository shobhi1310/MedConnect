package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerHomePage extends BaseActivity {
    private RecyclerView mRecyclerView;
    private CustomerBookingHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<CustomerBookingHistoryCard> orders;
    boolean doubleBackToExitPressedOnce = false;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_customer_home_page);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Home");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bookMedicine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerHomePage.this,SearchMedicineActivity.class);
                startActivity(i);
            }
        });
        queue= Volley.newRequestQueue(this);
        createExampleList();
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        if(orders.size()>0) {
            mRecyclerView = findViewById(R.id.customerBookingHistoryRecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayout = new LinearLayoutManager(this);
            mAdapter = new CustomerBookingHistoryAdapter(orders);
            mRecyclerView.setLayoutManager(mLayout);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            TextView t = findViewById(R.id.noBookingsPrompt);
            t.setVisibility(View.VISIBLE);
        }
    }

    public void onSearchMedicine(View view){
        Intent i = new Intent(this,BookMedicine.class);
        startActivity(i);

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

    public void createExampleList() {
        orders= new ArrayList<CustomerBookingHistoryCard>();
        Intent intent = getIntent();
        this.APICall("lala");


    }
    private void APICall(String id){
        String url = "https://glacial-caverns-39108.herokuapp.com/booking/current/5f467f770a31d232e88916e9";

        queue.cancelAll("CurrentBookings");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<CustomerBookingHistoryCard> currentList = new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("currentBooking");
                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject = result.getJSONObject(i);
                                JSONObject medicine = jsonObject.getJSONObject("medicine_id");
                                JSONObject shop = jsonObject.getJSONObject("shop_id");
                                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                String string1 = jsonObject.getString("createdAt");
                                Date result1 = df1.parse(string1);
                                String dateString = result1.toString();
                                Log.d("date",dateString);
                                currentList.add(new CustomerBookingHistoryCard(medicine.getString("name"),medicine.getString("strength"),medicine.getString("manufacturer"),shop.getString("name"),shop.getString("address"),shop.getString("phone"),dateString));
                            }
                        }catch (JSONException e) {
                            Toast.makeText(CustomerHomePage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        orders = currentList;
                        buildRecyclerView();
                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(CustomerHomePage.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("CurrentBookings");
        queue.add(stringRequest);
    }
}