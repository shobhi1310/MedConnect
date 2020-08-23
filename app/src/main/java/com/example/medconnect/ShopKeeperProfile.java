package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShopKeeperProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_keeper_profile);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Your Profile");
    }
}