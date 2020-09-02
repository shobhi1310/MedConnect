package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class successLayover extends AppCompatActivity {
    Button homePageRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_layover);

        TextView bookedMedicineName = findViewById(R.id.bookedMedicineDetails);
        TextView bookedMedicineFromShopName = findViewById(R.id.bookedFromShopName);
        TextView bookedMedicineFromShopAddress = findViewById(R.id.bookedFromShopAddress);
        homePageRedirect = findViewById(R.id.homeRedirect);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            bookedMedicineName.setText((CharSequence) extras.get("medicine_name"));
            bookedMedicineFromShopName.setText((CharSequence) extras.get("shop_name"));
            bookedMedicineFromShopAddress.setText((CharSequence) extras.get("shop_address"));
        }
        homePageRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(successLayover.this,CustomerHomePage.class);
                startActivity(i);
            }
        });
    }
}