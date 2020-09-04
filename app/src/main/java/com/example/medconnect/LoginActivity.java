package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medconnect.ui.main.CustomerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    String Email;
    String Password;
    RadioButton selectedRadioBtn;
    RadioGroup custOrShop;
    String CustomerOrShopOwner;
    CustomSpinner customSpinner;
    private RequestQueue queue;
    public static final String Data = "StoredData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.userLogin);

        queue = Volley.newRequestQueue(this);
//        Intent i = getIntent();
//        isLoggedOut = i.getBooleanExtra("loggedOut", false);

        custOrShop = findViewById(R.id.customerOrShopOwner);

        customSpinner= new CustomSpinner(LoginActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = email.getText().toString();
                Password = password.getText().toString();
                int selectedId = custOrShop.getCheckedRadioButtonId();
                selectedRadioBtn = findViewById(selectedId);
                if(selectedId == -1) {
                    Toast.makeText(LoginActivity.this,"Select either Customer or Shop Owner", Toast.LENGTH_SHORT).show();
                }
//                else {
                CustomerOrShopOwner = selectedRadioBtn.getText().toString();
//                    Toast.makeText(LoginActivity.this, CustomerOrShopOwner, Toast.LENGTH_SHORT).show();
//                }

                boolean isCustomer = false;

                if(CustomerOrShopOwner.equals("CUSTOMER")) {
                    isCustomer = true;
                    Log.d("test", "True");
                }

                APICall(Email, Password, isCustomer);

            }
        });
    }

    private void APICall(final String email, final String password, final boolean isCustomer) {
        customSpinner.startSpinner();
        String url="https://glacial-caverns-39108.herokuapp.com/user/login";
        queue.cancelAll("UserLogin");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        Log.d("LoginError", response);
                        if(response.equals("null") || response.equals("{}")) {
                            Toast.makeText(getApplicationContext(), "Your email or password is wrong", Toast.LENGTH_SHORT).show();
                            customSpinner.dismissSpinner();
                        }
                        else {
                            try {
                                saveData(response, isCustomer);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            Intent intent;
                            if(isCustomer) {
                                intent = new Intent(LoginActivity.this, CustomerHomePage.class);
                            }
                            else {
                                intent = new Intent(LoginActivity.this, ShopOwnerHome.class);
                            }
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "bye register", Toast.LENGTH_LONG).show();
                Log.d("Error.Response", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                String res = "true";
                params.put("email", email);
                params.put("password",password);
                params.put("isCustomer", Boolean.toString(isCustomer));

                return params;
            }
        };
        stringRequest.setTag("UserLogin");

        queue.add(stringRequest);
    }

    public void saveData(String response, final boolean isCustomer) throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        JSONObject jsonObject = new JSONObject(response);
        String id = jsonObject.getString("_id");
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email_id");
        String phone = jsonObject.getString("phone");

        if(isCustomer == false) {
            String address = jsonObject.getString("address");
            editor.putString("ADDRESS", address);
            editor.putString("LOGGEDINAS", "SHOPOWNER");
        } else {
            editor.putString("LOGGEDINAS", "CUSTOMER");
        }

//        Toast.makeText(getApplicationContext(), id + " " + name, Toast.LENGTH_LONG).show();

        editor.putString("ID", id);
        editor.putString("NAME", name);
        editor.putString("EMAIL", email);
        editor.putString("PHONE", phone);

        editor.apply();
    }
//    @Override
//    public void onBackPressed() {
//        if(isLoggedOut) {
//            finishAffinity();
//        }
//    }
}