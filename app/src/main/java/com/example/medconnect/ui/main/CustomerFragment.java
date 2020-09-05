package com.example.medconnect.ui.main;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class CustomerFragment extends Fragment {
    EditText name;
    EditText mob;
    EditText email;
    EditText password;
    Button register;
    String Name;
    String Mobile;
    String Email;
    String Password;
    CustomSpinner customSpinner;
    RequestQueue queue ;
    public static final String Data = "StoredData";
    Utils utils;


    public void APIcallForRegistration(final String name, final String email,final String phone, final String password) {
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
                        intent.putExtra("customer", true);
                        startActivity(intent);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customSpinner.dismissSpinner();
                        Toast.makeText(getActivity(), "bye register", Toast.LENGTH_LONG).show();
                        Log.d("Error.Response", String.valueOf(error));
                    }
        }){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                String res = "true";
                params.put("name",name);
                params.put("email", email);
                params.put("phone",phone);
                params.put("password",password);
                params.put("isCustomer",res);

                return params;
            }

        };

        stringRequest.setTag("CustomerFragment");

    // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_fragment, container, false);
//        queue =  Volley.newRequestQueue(this);
        name = (EditText) view.findViewById(R.id.customerName);
        mob = (EditText) view.findViewById(R.id.customerMobile);
        email = (EditText) view.findViewById(R.id.customerEmail);
        password = (EditText) view.findViewById(R.id.customerPassword);
        register = (Button)view.findViewById(R.id.customerRegister);
        customSpinner= new CustomSpinner(this.getActivity());
//        utils=new Utils();
//        utils.autoHideKeyboard(view.findViewById(android.R.id.content).getRootView(), this.getActivity());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Mobile = mob.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();

                //now we will register user(customer and not shopOwner)
                APIcallForRegistration(Name, Email, Mobile, Password);

//                Intent intent = new Intent(getActivity(), GetStartedActivity.class);
//                intent.putExtra("customer", true);
//                startActivity(intent);

            }
        });

        return view;
    }

    public void saveData(String response) throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Data, Context.MODE_PRIVATE);
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
