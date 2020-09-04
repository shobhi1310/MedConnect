package com.example.medconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopOwnerSearchMedicine extends BaseActivity1 {



    private RecyclerView mRecyclerView;
    private ShopOwnerSearchMedicineAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ShopOwnerSearchMedicineCard> medicineList;
    private RequestQueue queue;
    private ProgressBar spinner;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState, R.layout.activity_shopowner_search_medicine);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Medicine");
        queue = Volley.newRequestQueue(this);
        utils = new Utils();

        spinner = (ProgressBar)findViewById(R.id.progress_loader);





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

        this.APICall(s);
//        Log.d("length of array",Integer.toString(filteredList.size()));
//        for(ShopOwnerSearchMedicineCard medItem:this.medicineList){
//            if(medItem.getMedicineName().toLowerCase().contains(s.toLowerCase())){
//                filteredList.add(medItem);
//            }
//        }

//        this.mRecyclerViewAdapter.filterList(filteredList);
    }



    private void createList(){
        this.medicineList = new ArrayList<>();

//        this.medicineList.add(new ShopOwnerSearchMedicineCard("Paracetamol","XYZ","150MG"));
//        this.medicineList.add(new ShopOwnerSearchMedicineCard("Crocin","XYZ","150MG"));
//        this.medicineList.add(new ShopOwnerSearchMedicineCard("Dolo","XYZ","150MG"));
//        this.medicineList.add(new ShopOwnerSearchMedicineCard("Shubhankar","XYZ","150MG"));
//        this.medicineList.add(new ShopOwnerSearchMedicineCard("Tapish","XYZ","150MG"));
//        this.medicineList.add(new ShopOwnerSearchMedicineCard("Rohit","XYZ","150MG"));
//        this.medicineList.add(new ShopOwnerSearchMedicineCard("Sameed","XYZ","150MG"));

    }

    private void buildRecycleView(){

        this.mRecyclerView = findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);

        this.mRecyclerViewAdapter = new ShopOwnerSearchMedicineAdapter(this.medicineList);

        this.mRecyclerViewAdapter = new ShopOwnerSearchMedicineAdapter(medicineList);

        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mRecyclerViewAdapter);

        this.mRecyclerViewAdapter.setOnItemCLickListener(new ShopOwnerSearchMedicineAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(ShopOwnerSearchMedicine.this, "clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ShopOwnerSearchMedicine.this, ShopOwnerAddMedicine.class);
                intent.putExtra("medicine", medicineList.get(position).getMedicineName());
                intent.putExtra("manufacturer", medicineList.get(position).getManufacturer());
                intent.putExtra("strength", medicineList.get(position).getWeight());
                intent.putExtra("id", medicineList.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void APICall(String s){
        spinner.setVisibility(View.VISIBLE);
        String url="https://glacial-caverns-39108.herokuapp.com/medicine/fetch/"+s;

        queue.cancelAll("MedicineList");
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        ArrayList<ShopOwnerSearchMedicineCard> filteredList=new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("medicines");
//                                    .getJSONObject("list");
//                            int maxItems = result.getInt("end");
//                            JSONArray resultList = result.getJSONArray("item");
                            //this.medicineList.add(new ShopOwnerSearchMedicineCard("Paracetamol","XYZ","150MG"));

                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject= result.getJSONObject(i);
                                Log.d("JSON Result",jsonObject.getString("name"));
                                filteredList.add(new ShopOwnerSearchMedicineCard(jsonObject.getString("_id"),jsonObject.getString("name"),jsonObject.getString("manufacturer"),jsonObject.getString("strength")));

                            }

                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(ShopOwnerSearchMedicine.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        medicineList=filteredList;

                        spinner.setVisibility(View.GONE);


                        TextView t = findViewById(R.id.searchMedicinePrompt);
                        if(medicineList.size()>0){
                            utils.hideKeyboard(findViewById(android.R.id.content).getRootView(),ShopOwnerSearchMedicine.this);
                            t.setVisibility(View.INVISIBLE);
                        }
                        else{
                            t.setVisibility(View.VISIBLE);
                        }


                       mRecyclerViewAdapter.filterList(filteredList);
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(ShopOwnerSearchMedicine.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("MedicineList");

        // executing the request (adding to queue)
        queue.add(stringRequest);

    }

}