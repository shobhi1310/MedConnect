package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicineDetails extends BaseActivity {

    Integer quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_medicine_details);
//        setContentView();
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Details");
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


        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quantity = (int) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MedicineDetails.this,GoogleMapPage.class);
                startActivity(intent);
            }
        });

    }

//    public ArrayList<String> getMedicineDetails(String name, ImageView img, int weight, String mfg, boolean prescription, ArrayList<String>) {
//        medicine_name.setText(name);
//        medicine_weight.setText(String.valueOf(weight));
//        mfg_name.setText(mfg);
//        medicine_image
//    }
}