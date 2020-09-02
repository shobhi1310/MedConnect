package com.example.medconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class SearchMedicineActivity extends BaseActivity {


    MaterialSearchView searchView;
    private RecyclerView mRecyclerView;
    private medicineAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MedicineItem> medicineList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_search_medicine);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Search Medicine");
        queue= Volley.newRequestQueue(this);

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
//        ArrayList<MedicineItem> filteredList = new ArrayList<>();
//        for(MedicineItem medItem:this.medicineList){
//            if(medItem.getMedicineName().toLowerCase().contains(s.toLowerCase())){
//                filteredList.add(medItem);
//            }
//        }
//
//        this.mRecyclerViewAdapter.filterList(filteredList);

        this.APICall(s);
    }



    private void createList(){
        this.medicineList = new ArrayList<>();



    }

    private void buildRecycleView(){

        this.mRecyclerView = findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);

        this.mRecyclerViewAdapter = new medicineAdapter(this.medicineList);

        this.mRecyclerViewAdapter = new medicineAdapter(medicineList);

        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mRecyclerViewAdapter);

        this.mRecyclerViewAdapter.setOnItemCLickListener(new medicineAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(SearchMedicineActivity.this, "clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchMedicineActivity.this, SelectShop.class);
                intent.putExtra("id",medicineList.get(position).getId());
//                Intent intent1= new Intent(SearchMedicineActivity.this,MedicineDetails.class);
                intent.putExtra("Medicine",medicineList.get(position));
                startActivity(intent);
            }
        });

    }



    private void APICall(String s){
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
                        ArrayList<MedicineItem> filteredList=new ArrayList<>();
                        try {
                            JSONArray result = new JSONObject(response).getJSONArray("medicines");
//                                    .getJSONObject("list");
//                            int maxItems = result.getInt("end");
//                            JSONArray resultList = result.getJSONArray("item");
                            //this.medicineList.add(new ShopOwnerSearchMedicineCard("Paracetamol","XYZ","150MG"));



                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject= result.getJSONObject(i);
                                Log.d("JSON Result",jsonObject.getString("name"));
                                filteredList.add(new MedicineItem(jsonObject.getString("_id"),jsonObject.getString("name"),jsonObject.getString("manufacturer"),jsonObject.getString("strength")));

                            }





                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(SearchMedicineActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        medicineList=filteredList;

                        TextView t = findViewById(R.id.searchMedicinePrompt);
                        if(medicineList.size()>0){
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
                        Toast.makeText(SearchMedicineActivity.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag("MedicineList");

        // executing the request (adding to queue)
        queue.add(stringRequest);


    }

}