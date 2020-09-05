package com.example.medconnect.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medconnect.CustomSpinner;
import com.example.medconnect.GetStartedActivity;
import com.example.medconnect.LoginActivity;
import com.example.medconnect.R;
import com.example.medconnect.SearchMedicineActivity;
import com.example.medconnect.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class ShopOwnerFragment extends Fragment {
    private static final String TAG = "ShopOwnerFragment";
    EditText shopname;
    EditText mob;
    EditText email;
    EditText password;
    EditText address;
    EditText license;
    Button register;
    String ShopName;
    String Mobile;
    String Email;
    String Password;
    String Address;
    String License;
    CustomSpinner customSpinner;
    Utils utils;
    public static final String Data = "StoredData";

    public void APIcallForRegistration(final String shopName, final String email,final String phone, final String password,final String address, final String license) {
        String url = "https://glacial-caverns-39108.herokuapp.com/user/register";
        customSpinner.startSpinner();
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        try {
                            saveData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getActivity(), GetStartedActivity.class);
                        intent.putExtra("customer", false);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "bye register", Toast.LENGTH_LONG).show();
                customSpinner.dismissSpinner();
                Log.d("Error.Response", String.valueOf(error));
            }
        }){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                String res = "false";
                params.put("name",shopName);
                params.put("email", email);
                params.put("phone",phone);
                params.put("password",password);
                params.put("address",address);
                params.put("license",license);
                params.put("isCustomer",res);
                return params;
            }

        };

        stringRequest.setTag("ShopOwnerFragment");

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_owner_fragment, container, false);

        shopname = (EditText) view.findViewById(R.id.shopName);
        mob = (EditText) view.findViewById(R.id.shopMobile);
        email = (EditText) view.findViewById(R.id.shopEmail);
        password = (EditText) view.findViewById(R.id.shopPassword);
        address = (EditText)view.findViewById(R.id.shopAddress);
        license = (EditText)view.findViewById(R.id.shopLicense);
        register = (Button) view.findViewById(R.id.shopRegister);
        customSpinner= new CustomSpinner(this.getActivity());
//        utils=new Utils();
//        utils.autoHideKeyboard(view.findViewById(android.R.id.content).getRootView(), this.getActivity());


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopName = shopname.getText().toString();
                Mobile = mob.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();
                Address = address.getText().toString();
                License = license.getText().toString();

                APIcallForRegistration(ShopName,Email,Mobile,Password,Address,License);

//                Toast.makeText(getActivity(), ShopName + " " + Mobile + " " + Email + " " + Password, Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(), GetStartedActivity.class);
//                intent.putExtra("customer",false);
//                startActivity(intent);

            }
        });

        return view;
    }
    public void saveData(String response) throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Data, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        JSONObject jsonObject = new JSONObject(response);
        String id = jsonObject.getString("_id");
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email_id");
        String phone = jsonObject.getString("phone");
        String address = jsonObject.getString("address");
//        Toast.makeText(getApplicationContext(), id + " " + name, Toast.LENGTH_LONG).show();

        editor.putString("LOGGEDINAS", "SHOPOWNER");
        editor.putString("ID", id);
        editor.putString("NAME", name);
        editor.putString("EMAIL", email);
        editor.putString("PHONE", phone);
        editor.putString("ADDRESS", address);

        editor.apply();
    }

}
