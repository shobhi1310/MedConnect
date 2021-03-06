//package com.example.medconnect;
//
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//public class CustomerEditProfile extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.customer_editprofile);
//
//        Toolbar toolbar= findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar= getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        TextView toolbar_title = findViewById(R.id.toolbar_title);
//        toolbar_title.setText("Edit Profile");
//
//    }
//}

package com.example.medconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medconnect.CustomSpinner;
import com.example.medconnect.GetStartedActivity;
import com.example.medconnect.R;
import com.example.medconnect.SearchMedicineActivity;
import com.example.medconnect.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerEditProfile extends AppCompatActivity {
    EditText name;
    EditText mob;
    Button Edit;
    String Name;
    String Mobile;
    CustomSpinner customSpinner;
    private RequestQueue queue;
    public static final String Data = "StoredData";


    public void APIcallForUpdate(final String mobile,final String name,final String id) {
        String url = "https://glacial-caverns-39108.herokuapp.com/user/profile/update/"+id;
        customSpinner.startSpinner();

        queue.cancelAll("EditProfile");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(CustomerEditProfile.this, response, Toast.LENGTH_LONG).show();
                        try {
                            saveData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(CustomerEditProfile.this, CustomerProfile.class);
                        startActivity(intent);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customSpinner.dismissSpinner();
                Toast.makeText(CustomerEditProfile.this, "Server is not responding...", Toast.LENGTH_LONG).show();
                Log.d("Error.Response", String.valueOf(error));
            }
        }){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                String res = "true";
                params.put("name",name);
//                params.put("email", email);
                params.put("phone",mobile);
//                params.put("password",password);
                params.put("isCustomer",res);

                return params;
            }

        };

        stringRequest.setTag("EditProfile");
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_editprofile);

//        email = findViewById(R.id.customerEmail);
        name = findViewById(R.id.customerName);
        Edit = (Button)findViewById(R.id.customerUpdate);
        mob = findViewById(R.id.customerMobile);
//        utils=new Utils();
//        utils.autoHideKeyboard(findViewById(android.R.id.content).getRootView(),LoginActivity.this);
        queue = Volley.newRequestQueue(this);
//        Intent i = getIntent();
//        isLoggedOut = i.getBooleanExtra("loggedOut", false);
//        custOrShop = findViewById(R.id.customerOrShopOwner);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle=findViewById(R.id.toolbar_title);
        toolbarTitle.setText("");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        customSpinner= new CustomSpinner(CustomerEditProfile.this);
        SharedPreferences data = this.getSharedPreferences(Data,MODE_PRIVATE);

        final String customerId = data.getString("ID","");

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Email = email.getText().toString();
                Mobile = mob.getText().toString();
                Name = name.getText().toString();
                Log.d("profile-details", Mobile + " " + Name + " " + customerId);
                APIcallForUpdate(Mobile, Name, customerId);

            }
        });
    }

    public void saveData(String response) throws JSONException {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Data, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        JSONObject jsonObject = new JSONObject(response);
        String id = jsonObject.getString("_id");
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email_id");
        String phone = jsonObject.getString("phone");

//        Toast.makeText(getApplicationContext(), id + " " + name, Toast.LENGTH_LONG).show();
        editor.putString("LOGGEDINAS", "CUSTOMER");
        editor.putString("ID", id);
        editor.putString("NAME", name);
        editor.putString("EMAIL", email);
        editor.putString("PHONE", phone);

        editor.apply();
    }

}

