package com.example.medconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopOwnerAddMedicine extends AppCompatActivity {

    RequestQueue queue;
    public static final String Data = "StoredData";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add_medicine);

        final Intent intent= getIntent();
        queue= Volley.newRequestQueue(this);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView medicine= findViewById(R.id.medicine);
        TextView manufacturer= findViewById(R.id.manufacturer);
        TextView strength= findViewById(R.id.strength);
        Button addButton =findViewById(R.id.addButton);


        String textSelected = "In Stock";

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup=findViewById(R.id.statusRadio);
                int checkedButtonID= radioGroup.getCheckedRadioButtonId();
                String textSelected = (String) ((RadioButton)findViewById(checkedButtonID)).getText();
                Log.d("Text Select",textSelected);
                addMedicineAPI(intent.getStringExtra("id"),textSelected);

                Intent intent1= new Intent(ShopOwnerAddMedicine.this,ShopOwnerHome.class);
                startActivity(intent1);

            }
        });
        medicine.setText(intent.getStringExtra("medicine"));
        manufacturer.setText(intent.getStringExtra("manufacturer"));
        strength.setText(intent.getStringExtra("strength"));
    }

    private void addMedicineAPI(String id, final String textButton){
        SharedPreferences sharedPreferences= getSharedPreferences(Data,MODE_PRIVATE);
        String shopID=sharedPreferences.getString("ID","");

        String url="https://glacial-caverns-39108.herokuapp.com/shop/"+shopID+"/addMedicine/"+id;

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
                }){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                if(textButton.equals("In Stock")){
                    params.put("status","true");
                }else{
                    params.put("status","false");
                }
                return params;
            }

        };
        stringRequest.setTag("Add Medicine");

        // executing the request (adding to queue)
        queue.add(stringRequest);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
