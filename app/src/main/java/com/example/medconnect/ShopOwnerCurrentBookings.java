package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShopOwnerCurrentBookings extends  BaseActivity1{
    private RecyclerView mRecyclerView;
    private ShopOwnerCurrentBookingsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<ShopOwnerCurrentBookingsCard> orders;
    private RequestQueue queue;
    String shopOwnerId;
    private MenuItem item;
    private NavigationView nav;
    public static final String Data = "StoredData";
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.shop_owner_current_bookings);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Current Bookings");
        nav = findViewById(R.id.navigation);
        item = nav.getMenu().getItem(3);
        item.setEnabled(false);

        queue= Volley.newRequestQueue(this);
        spinner=findViewById(R.id.progress_loader);
        createExampleList();
//        buildRecyclerView();
    }

    public void createExampleList() {
        orders= new ArrayList<ShopOwnerCurrentBookingsCard>();
//        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
//        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Tapish","99999999999","12/07/20"));
//        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
//        orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        //orders.add(new ShopOwnerCurrentBookingsCard("Paracetamol","150MG","XYZ","Sameed","99999999999","12/07/20"));
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
        shopOwnerId = sharedPreferences.getString("ID", "");
        this.APICall(shopOwnerId);

    }

    public void buildRecyclerView() {

            mRecyclerView=findViewById(R.id.shopOwnerCurrentBookingsRecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayout = new LinearLayoutManager(this);
            mAdapter = new ShopOwnerCurrentBookingsAdapter(orders);
            mRecyclerView.setLayoutManager(mLayout );
            mRecyclerView.setAdapter(mAdapter);




    }

    private void APICall(String id){
        String url = "https://glacial-caverns-39108.herokuapp.com/booking/current/" + id;
        spinner.setVisibility(View.VISIBLE);

        queue.cancelAll("CurrentBookings");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<ShopOwnerCurrentBookingsCard> pastList = new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("currentBooking");
                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject = result.getJSONObject(i);
                                JSONObject medicine = jsonObject.getJSONObject("medicine_id");
                                JSONObject customer = jsonObject.getJSONObject("customer_id");
                                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                String string1 = jsonObject.getString("createdAt");
                                Date result1 = df1.parse(string1);
                                String dateString = result1.toString();
                                Log.d("date",dateString);
                                pastList.add(new ShopOwnerCurrentBookingsCard(medicine.getString("name"),medicine.getString("strength"),medicine.getString("manufacturer"),customer.getString("name"),customer.getString("phone"),dateString,jsonObject.getString("deadline")));
                            }
                        }catch (JSONException e) {
                            Toast.makeText(ShopOwnerCurrentBookings.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        orders = pastList;
                        spinner.setVisibility(View.GONE);
                        LinearLayout t=findViewById(R.id.currentBookingPrompt);
                        if(orders.size()>0){
                            t.setVisibility(View.GONE);
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
                        Toast.makeText(ShopOwnerCurrentBookings.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("CurrentBookings");
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
