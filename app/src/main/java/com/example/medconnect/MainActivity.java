package com.example.medconnect;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    MaterialSearchView searchView;
    private RecyclerView mRecyclerView;
    private medicineAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MedicineItem> medicineList;

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



        /*

            this searching logic is for customers
        */

        //create a list instead of database(which we will be using after few days)

        /*

        *
        * my logic
        * for search bar ,which customer is going to use,for that when we will be using database ,we will keep some priority
        * for some medicine whenever customer uses search bar to find medicine,we will be retrieving only 10 medicine items(if possible)
        * from database based on two criteria:-
        *
        *   1.)Firstly,we will check whether that particular medicine item(which contains shop name,license no,location as parameters) is available or not.Then,we will try to display those shops or those
        *       medicine items which contains stock of that particular medicine.
        *   2.)If all or more that 10 medicine items exists in database where that particular medicine is available at that particular time,then our
        *       search algorithm will try to find top 10 nearest shops with available tag.
        *
        *
        *
        * Applying this in our app will reduce mir sameed's work regarding map API as he just needs to fetch user's current location and shop location which he can easily fetch
        * the medicineItem.Afterwards,google map api will try to show user shortest path from that particular location.
        *
        *
        * */

        //MedicineItem is a class consists of 3 member variables

        /*
        * 1.) private imageR
        * 2.) private text1(name of medicine)
        * 3.) private (manufacturer)
        * few more parameters will be added,this component i am making just for demo purpose
        * in reality ,we need to use mir sameed customer component instead of this dummy component
        * Also,we need to change some few member variables and we may need to add more member variables
        * That component should be clickable.
        * */


        this.createList();
        this.buildRecycleView();


        EditText text = findViewById(R.id.searchBox);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });









    }


    private void filter(String s){
        ArrayList<MedicineItem> filteredList = new ArrayList<>();
        for(MedicineItem medItem:this.medicineList){
            if(medItem.getText1().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(medItem);
            }
        }

        this.mRecyclerViewAdapter.filterList(filteredList);
    }



    private void createList(){
        this.medicineList = new ArrayList<>();


        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"Medicine Name","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"paranormal","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"crocin","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"crocin advance","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"ointment","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"sddsds","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"sdsffsddffd","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"jlinkkj","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"dsvdssdvss","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"efwfdssfsdf","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"wqeewe","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"ferferfe","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"chiggi","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"shubs","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"tapish","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"mir","description"));
        this.medicineList.add(new MedicineItem(R.drawable.medicine_icon,"rohit","description"));



    }

    private void buildRecycleView(){

        this.mRecyclerView = findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);


        this.mRecyclerViewAdapter = new medicineAdapter(medicineList);

        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mRecyclerViewAdapter);

    }
}