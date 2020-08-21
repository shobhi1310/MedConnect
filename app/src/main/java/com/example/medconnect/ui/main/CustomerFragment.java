package com.example.medconnect.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medconnect.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_fragment, container, false);

        name = (EditText)view.findViewById(R.id.customerName);
        mob = (EditText)view.findViewById(R.id.customerMobile);
        email = (EditText)view.findViewById(R.id.customerEmail);
        password = (EditText)view.findViewById(R.id.customerPassword);
        register = (Button)view.findViewById(R.id.customerRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Mobile = mob.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();

                Toast.makeText(getActivity(), Name + " " + Mobile + " " + Email + " " + Password, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
