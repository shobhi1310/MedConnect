package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerBookingHistory extends  BaseActivity{
    private RecyclerView mRecyclerView;
    private CustomerBookingHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<CustomerBookingHistoryCard> orders;
    private RequestQueue queue;

    private ProgressBar spinner;

    SwipeRefreshLayout swipe;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_customer_booking_history);

        swipe = findViewById(R.id.swipeToRefresh);
        swipe.setColorSchemeColors(R.color.background);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Booking History");
        queue= Volley.newRequestQueue(this);
        spinner=findViewById(R.id.progress_loader);
        createExampleList();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shuffle();
                swipe.setRefreshing(false);
            }
        });

    }

    private void shuffle() {
        queue= Volley.newRequestQueue(this);
        createExampleList();
    }

    public void createExampleList() {
        orders= new ArrayList<CustomerBookingHistoryCard>();
        Intent intent = getIntent();
        this.APICall("lala");
    }

    public void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.customerBookingHistoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new CustomerBookingHistoryAdapter(orders);

        mRecyclerView.setLayoutManager(mLayout );
        mRecyclerView.setAdapter(mAdapter);

    }

    private void APICall(String id){
        spinner.setVisibility(View.VISIBLE);
        String url = "https://glacial-caverns-39108.herokuapp.com/booking/past/5f467f770a31d232e88916e9";

        queue.cancelAll("PastBookings");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<CustomerBookingHistoryCard> currentList = new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("AllPastBookings");
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
                            Toast.makeText(CustomerBookingHistory.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        orders = currentList;

                        spinner.setVisibility(View.GONE);


                        TextView t = findViewById(R.id.bookingHistoryPrompt);
                        if(orders.size()>0){
                            t.setVisibility(View.INVISIBLE);
                        }
                        else{
                            t.setVisibility(View.VISIBLE);
                        }



                        buildRecyclerView();
                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(CustomerBookingHistory.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("PastBookings");
        queue.add(stringRequest);
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
