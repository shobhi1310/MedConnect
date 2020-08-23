package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomerProfile extends BaseActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState, R.layout.activity_customer_profile);
        }catch (Error e){
            e.printStackTrace();
        }
//        setContentView(R.layout.activity_customer_profile);
    }
}