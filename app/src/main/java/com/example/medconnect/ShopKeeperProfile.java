package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShopKeeperProfile extends BaseActivity1 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_shop_keeper_profile);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Your Profile");
    }
}