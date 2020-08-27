package com.example.medconnect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookMedicine extends AppCompatActivity {

    private int timeRange;
    private ArrayList<Integer> time = new ArrayList<Integer>(){
        {add(15); add(30); add(45); add(60); add(90); add(120);}
    };
    private Button book;
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

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        book = (Button) findViewById(R.id.book_button);
        if(seekBar!=null){
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
//                    Toast.makeText(BookMedicine.this, "Current value is " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                    timeRange = seekBar.getProgress();
                }
            });
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookMedicine.this, "Current value is " + time.get(timeRange), Toast.LENGTH_SHORT).show();
            }
        });
    }
}