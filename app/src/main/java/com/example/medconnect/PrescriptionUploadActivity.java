package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionUploadActivity extends AppCompatActivity {
    Button select;
    TextView file;
    Button upload;
    ImageView imagePreview;
    Integer quantity = 0;
    SelectShopCard shop=null;
    MedicineItem medicineItem=null;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_upload);
        select = findViewById(R.id.selectPrescription);
        file = findViewById(R.id.fileName);
        upload = findViewById(R.id.uploadPrescription);
        imagePreview = findViewById(R.id.previewImage);
        Intent intent= getIntent();
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Upload Prescription");
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        upload.setVisibility(View.INVISIBLE);
        file.setVisibility(View.INVISIBLE);

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
            imagePreview.setImageURI(data.getData());
            upload.setVisibility(View.VISIBLE);
//            file.setVisibility(View.VISIBLE);
//            path = getPathFromURI(data.getData());
//            Log.d("ImagePath",path);
//            file.setText(path);
        } else {
            Toast.makeText(PrescriptionUploadActivity.this, "please select a file..", Toast.LENGTH_SHORT).show();
        }
    }
    private void selectImage(){
        //to offer user to select file from manager
        Intent intent = new Intent();
        intent.setType("image/jpeg");
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
            selectImage();
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}