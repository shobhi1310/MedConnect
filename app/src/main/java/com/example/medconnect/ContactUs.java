package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class ContactUs extends BaseActivity {

    private MenuItem item;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_contact_us);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Contact Us");

        nav = findViewById(R.id.navigation);
        item = nav.getMenu().getItem(5);
        item.setEnabled(false);

        final String[] names={"Chirag Gupta","Mir Sameed Ali","Subhankar Badhra","Tapish Ojha","Rohit Shakya"};
        final String[] emails={"cs18b006@iittp.ac.in","cs18b021@iittp.ac.in","cs18b034@iittp.ac.in","cs18b038@iittp.ac.in","cs18b029@iittp.ac.in"};

        ListAdapter listAdapter = new ContactRowAdapter(this,names);
        ListView myListView = findViewById(R.id.myListView);
        myListView.setAdapter(listAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //String fruit = String.valueOf(adapterView.getItemAtPosition(i));
                        //Toast.makeText(AboutUs.this,names[i],Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ContactUs.this,SendEmail.class);
                        intent.putExtra("email",emails[i]);
                        startActivity(intent);

                    }
                }
        );

    }
}