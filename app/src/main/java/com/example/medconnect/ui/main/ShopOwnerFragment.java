package com.example.medconnect.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medconnect.GetStartedActivity;
import com.example.medconnect.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopName = shopname.getText().toString();
                Mobile = mob.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();
                Address = address.getText().toString();
                License = license.getText().toString();

                Toast.makeText(getActivity(), ShopName + " " + Mobile + " " + Email + " " + Password, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), GetStartedActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
