package com.example.medconnect;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {


    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sideNavbar functionality
        final DrawerLayout drawerLayout = findViewById(R.id.sideNavbar);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        //searchBar functionality
        Toolbar toolbar =  (Toolbar)(findViewById(R.id.searchBar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search medicine");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
//
//        searchView = (MaterialSearchView)findViewById(R.id.search);


    }

    private void setSupportActionBar(Toolbar toolbar) {

    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_items,menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        searchView.setMenuItem(menuItem);
        return true;
    }



}