package com.example.medconnect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookMedicine extends AppCompatActivity {

    public static final String Data = "StoredData";
    private int timeRange;
    String medicine_id;
    RequestQueue queue;
    String customer_id;
    String shop_owner_id;
    private ArrayList<Integer> time = new ArrayList<Integer>(){
        {add(15); add(30); add(45); add(60); add(90); add(120);}
    };
    private Button book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_medicine);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Book Medicine");

        SelectShopCard shop = null;
        MedicineItem medicineItem = null;
        int quantity = 1;

        if(getIntent().getExtras() != null) {
            shop = (SelectShopCard) getIntent().getSerializableExtra("Shop");
            medicineItem = (MedicineItem) getIntent().getSerializableExtra("Medicine");
            quantity = getIntent().getIntExtra("Quantity", 1);
        }

        queue = Volley.newRequestQueue(this);

        TextView shopName= findViewById(R.id.shop_name);
        TextView medicineQuantity = findViewById(R.id.quantity);
        final TextView medicine_name = (TextView)findViewById(R.id.medicine_name);
        TextView medicine_weight = (TextView)findViewById(R.id.medicine_weight);
        TextView mfg_name = (TextView)findViewById(R.id.mfg_name);

        shopName.setText(shop.getShopName());
        medicineQuantity.setText("Quantity: " + Integer.toString(quantity));
        medicine_name.setText(medicineItem.getMedicineName());
        mfg_name.setText(medicineItem.getManufacturer());
        medicine_weight.setText(medicineItem.getWeight());

        medicine_id = medicineItem.getId();
        shop_owner_id = shop.getId();

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        book = (Button) findViewById(R.id.book_button);
        if(seekBar != null){
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
//                    Toast.makeText(BookMedicine.this, "Current value is " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                    timeRange = seekBar.getProgress();
                }
            });
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
                customer_id = sharedPreferences.getString("ID", "");
                APICall(customer_id, medicine_id, shop_owner_id, time.get(timeRange));
                Toast.makeText(BookMedicine.this, "Current value is " + time.get(timeRange), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void APICall(final String customer_id, final String medicine_id, final String shop_owner_id, final int timeRange) {
        String url = "https://glacial-caverns-39108.herokuapp.com/booking/book";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(BookMedicine.this, successLayover.class);
//                        startActivity(intent);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "bye register", Toast.LENGTH_LONG).show();
                Log.d("Error.Response", String.valueOf(error));
            }
        }){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                String res = "true";
                params.put("customer_id",customer_id);
                params.put("medicine_id", medicine_id);
                params.put("shop_id",shop_owner_id);
                params.put("booking_amount","100");
                params.put("time_range", Integer.toString(timeRange));

                return params;
            }
        };

        stringRequest.setTag("CustomerFragment");

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}