package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionUploadActivity extends AppCompatActivity {
    Button select;
    TextView file;
    Button upload;
    Integer quantity = 0;
    SelectShopCard shop=null;
    MedicineItem medicineItem=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_upload);
        select = findViewById(R.id.selectPrescription);
        file = findViewById(R.id.fileName);
        upload = findViewById(R.id.uploadPrescription);
        Intent intent= getIntent();



        if(intent.getExtras()!=null){
            shop= (SelectShopCard) intent.getSerializableExtra("Shop");
            medicineItem=(MedicineItem) intent.getSerializableExtra("Medicine");
            quantity = intent.getIntExtra("Quantity",0);
        }else{
            Log.d("Shop","Not Found");
        }
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
            }
        });
        // upload on CLick listener needs to be written
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionUploadActivity.this, BookMedicine.class);
                intent.putExtra("Shop", shop);
                intent.putExtra("Medicine", medicineItem);
                intent.putExtra("Quantity", quantity);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            //pdfUri = data.getData(); // return the uri of selected file
            file.setText("A file is selected : " + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(PrescriptionUploadActivity.this, "please select a file..", Toast.LENGTH_SHORT).show();
        }
    }
    private void selectPdf(){
        //to offer user to select file from manager
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        startActivityForResult(intent,86);
    }
    private void checkStoragePermission(){
        if (ContextCompat.checkSelfPermission(PrescriptionUploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            //permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(PrescriptionUploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Requires storage permission")
                        .setMessage("you have to give this permission to access the feature")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(PrescriptionUploadActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(PrescriptionUploadActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }
        }
        else{
            selectPdf();
        }
    }
}