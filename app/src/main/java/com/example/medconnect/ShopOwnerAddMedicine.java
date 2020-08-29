package com.example.medconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopOwnerAddMedicine extends AppCompatActivity {

    RequestQueue queue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add_medicine);

        final Intent intent= getIntent();
        queue= Volley.newRequestQueue(this);


        TextView medicine= findViewById(R.id.medicine);
        TextView manufacturer= findViewById(R.id.manufacturer);
        TextView strength= findViewById(R.id.strength);

        Button addButton =findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineAPI(intent.getStringExtra("id"));

                Intent intent1= new Intent(ShopOwnerAddMedicine.this,ShopOwnerHome.class);
                startActivity(intent1);

            }
        });
        medicine.setText(intent.getStringExtra("medicine"));
        manufacturer.setText(intent.getStringExtra("manufacturer"));
        strength.setText(intent.getStringExtra("strength"));

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Medicine");
    }

    private void addMedicineAPI(String id){

        String url="https://glacial-caverns-39108.herokuapp.com/shop/5f47e5ea174464ed81cc5100/addMedicine/"+id;

        queue.cancelAll("Add Medicine");
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {


                      


                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(ShopOwnerAddMedicine.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("Add Medicine");

        // executing the request (adding to queue)
        queue.add(stringRequest);

    }
}
