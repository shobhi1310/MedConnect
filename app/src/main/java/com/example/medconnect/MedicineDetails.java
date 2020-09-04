package com.example.medconnect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicineDetails extends AppCompatActivity {
    
    Integer quantity = 0;
    SelectShopCard shop=null;

    MedicineItem medicineItem=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Details");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button locate=(Button) findViewById(R.id.locateMap);
        TextView medicine_name = (TextView)findViewById(R.id.medicine_name);
        TextView medicine_weight = (TextView)findViewById(R.id.medicine_weight);
        TextView mfg_name = (TextView)findViewById(R.id.mfg_name);
        Button book = (Button)findViewById(R.id.book_button);
        ImageView medicine_image = (ImageView)findViewById(R.id.medicine_image);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(5);
        arrayList.add(10);

        //Intent
        Intent intent= getIntent();

        if(intent.getExtras()!=null){

             shop= (SelectShopCard) intent.getSerializableExtra("Shop");
             medicineItem=(MedicineItem) intent.getSerializableExtra("Medicine");
        }else{
            Log.d("Shop","Not Found");
        }


        TextView shopName= findViewById(R.id.shopName);
        TextView shopMobile= findViewById(R.id.shopMobile);
        TextView shopAddress= findViewById(R.id.shopAddress);
        TextView shopDistance= findViewById(R.id.shopDistance);



        shopName.setText(shop.getShopName());
        shopMobile.setText(shop.getShopMobile());
        shopAddress.setText(shop.getShopAddress());
        shopDistance.setText(shop.getDistance());

        medicine_name.setText(medicineItem.getMedicineName());
        mfg_name.setText(medicineItem.getManufacturer());
        medicine_weight.setText(medicineItem.getWeight());


        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quantity = (Integer) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
        

            }
        });

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MedicineDetails.this,GoogleMapPage.class);
                intent.putExtra("latitude",shop.getLatitude());
                intent.putExtra("longitude",shop.getLongitude());
                intent.putExtra("shopName",shop.getShopName());
                startActivity(intent);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!medicineItem.getPrescription()) {
                    Intent intent = new Intent(MedicineDetails.this, BookMedicine.class);
                    intent.putExtra("Shop", shop);
                    intent.putExtra("Medicine", medicineItem);
                    intent.putExtra("Quantity", quantity);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MedicineDetails.this, PrescriptionUploadActivity.class);
                    intent.putExtra("Shop", shop);
                    intent.putExtra("Medicine", medicineItem);
                    intent.putExtra("Quantity", quantity);
                    startActivity(intent);
                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
//    public ArrayList<String> getMedicineDetails(String name, ImageView img, int weight, String mfg, boolean prescription, ArrayList<String>) {
//        medicine_name.setText(name);
//        medicine_weight.setText(String.valueOf(weight));
//        mfg_name.setText(mfg);
//        medicine_image
//    }
}