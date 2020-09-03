package com.example.medconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    public static final String Data = "StoredData";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
        String decision = sharedPreferences.getString("LOGGEDINAS", "");
        if(decision.equals("CUSTOMER")) {
            intent = new Intent(SplashScreen.this, CustomerHomePage.class);
            startActivity(intent);
        } else if (decision.equals("SHOPOWNER")) {
            intent = new Intent(SplashScreen.this, ShopOwnerHome.class);
            startActivity(intent);
        } else {
            intent = new Intent(SplashScreen.this, IntroActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
