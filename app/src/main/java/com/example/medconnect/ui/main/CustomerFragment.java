package com.example.medconnect.ui.main;

import android.content.Intent;
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
import com.example.medconnect.GetStartedActivity;
import com.example.medconnect.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerFragment extends Fragment {
    private static final String TAG = "CustomerFragment";
    EditText name;
    EditText mob;
    EditText email;
    EditText password;
    Button register;
    String Name;
    String Mobile;
    String Email;
    String Password;
    RequestQueue queue ;


//    protected void OnCreate()

    //api will be called for registration purpose


    public void APIcallForRegistration(final String name, final String email,final String phone, final String password) {
        String url = "https://glacial-caverns-39108.herokuapp.com/user/register";


//        final TextView textView = (TextView) findViewById(R.id.text);
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), GetStartedActivity.class);
                        intent.putExtra("customer", true);
                        startActivity(intent);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
        //                textView.setText("That didn't work!");
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Mobile = mob.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();

                //now we will register user(customer and not shopOwner)
                APIcallForRegistration(Name, Email, Mobile, Password);



            }
        });

        return view;
    }
}
