package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerProfile extends BaseActivity {

    public static final String Data = "StoredData";
//    private RequestQueue queue;
    String name;
    String phone;
    String email;
//    public static String address;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_customer_profile);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Your Profile");
        Button profileEdit = (Button) findViewById(R.id.customer_profile_edit);
        TextView Name = findViewById(R.id.name);
        TextView Email = findViewById(R.id.email);
        TextView Phone = findViewById(R.id.phone);
        TextView Address = findViewById(R.id.address);

//        queue = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
        name = sharedPreferences.getString("NAME", "");
        email = sharedPreferences.getString("EMAIL", "");
        phone = sharedPreferences.getString("PHONE", "");
//        APICall(id);

        Name.setText(name);
        Phone.setText(phone);
//        Address.setText(address);
        Email.setText(email);

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerProfile.this,CustomerEditProfile.class);
                startActivity(intent);
            }
        });
    }

//    private void APICall(String id) {
//        String url = "https://glacial-caverns-39108.herokuapp.com/user/" + id;
//        queue.cancelAll("UserProfile");
//
//        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//                        try {
//                            JSONArray result = new JSONObject(response).getJSONArray("data");
//                            JSONObject jsonObject = result.getJSONObject(0);
////                            Log.d("json obj", jsonObject.toString());
//                            name = jsonObject.getString("name");
//                            email = jsonObject.getString("email_id");
//                            phone = jsonObject.getString("phone");
//
//                            Toast.makeText(getApplicationContext(), "name: " + name + "email " + email + "phone " + phone, Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // display a simple message on the screen
//                        Toast.makeText(getApplicationContext(), "Server is not responding", Toast.LENGTH_LONG).show();
//                    }
//                });
//        stringRequest.setTag("UserProfile");
//        queue.add(stringRequest);
//    }
}