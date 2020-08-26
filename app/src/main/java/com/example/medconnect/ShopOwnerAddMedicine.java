package com.example.medconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopOwnerAddMedicine extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add_medicine);

        Intent intent= getIntent();



        TextView medicine= findViewById(R.id.medicine);
        TextView manufacturer= findViewById(R.id.manufacturer);
        TextView strength= findViewById(R.id.strength);

        medicine.setText(intent.getStringExtra("medicine"));
        manufacturer.setText(intent.getStringExtra("manufacturer"));
        strength.setText(intent.getStringExtra("strength"));

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Medicine");
    }
}