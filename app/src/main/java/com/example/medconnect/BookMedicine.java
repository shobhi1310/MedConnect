package com.example.medconnect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class BookMedicine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_medicine);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Book Medicine");

        SelectShopCard shop = null;
        MedicineItem medicineItem = null;
        int quantity = 1;

        if(getIntent().getExtras() != null) {
            shop = (SelectShopCard) getIntent().getSerializableExtra("Shop");
            medicineItem = (MedicineItem) getIntent().getSerializableExtra("Medicine");
            quantity = getIntent().getIntExtra("Quantity", 1);
        }

        TextView shopName= findViewById(R.id.shop_name);
        TextView medicineQuantity = findViewById(R.id.quantity);
        TextView medicine_name = (TextView)findViewById(R.id.medicine_name);
        TextView medicine_weight = (TextView)findViewById(R.id.medicine_weight);
        TextView mfg_name = (TextView)findViewById(R.id.mfg_name);

        shopName.setText(shop.getShopName());
        medicineQuantity.setText("Quantity: " + Integer.toString(quantity));
        medicine_name.setText(medicineItem.getMedicineName());
        mfg_name.setText(medicineItem.getManufacturer());
        medicine_weight.setText(medicineItem.getWeight());


    }
}