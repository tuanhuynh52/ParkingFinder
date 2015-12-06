package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.DestinationDatabase.MySavedParkingLocation;
import com.example.tuanhuynh.parkingfinder.model.DestinationDatabase.ParkingLocationDB;

import java.util.List;

/**
 * Show list of saved parking locations by user
 */
public class MyDestination extends AppCompatActivity {

    private ListView mySavedListView;

    public ArrayAdapter<MySavedParkingLocation> myArrayAdapter;
    public List<MySavedParkingLocation> myList;
    private ParkingLocationDB mParkingLocationDB;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_destination);
        setTitle("My Destinations");

        mParkingLocationDB = new ParkingLocationDB(MyDestination.this);

        mySavedListView = (ListView) findViewById(R.id.MyPlaceListView);

        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        /*
         *Show listview
         */
        myList = mParkingLocationDB.getAllDataByUName(username);
        myArrayAdapter = new ArrayAdapter<MySavedParkingLocation>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, myList);
        mySavedListView.setAdapter(myArrayAdapter);
        if (myList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "EMPTY LIST",
                    Toast.LENGTH_SHORT).show();
        }
        /**
         * item click listener
         */
        mySavedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent next = new Intent(MyDestination.this, MyLocationDetail.class);
                Bundle extra = getIntent().getExtras();

                String locationName = myList.get(position).getLocation_name();
                String address = myList.get(position).getAddress();
                String type = myList.get(position).getType();
                String price = myList.get(position).getPrice();
                String description = myList.get(position).getDescription();
                extra.putString("extra_location", locationName);
                extra.putString("extra_address", address);
                extra.putString("extra_type", type);
                extra.putString("extra_price", price);
                extra.putString("extra_description", description);

                extra.putString("extra_name", username);

                next.putExtras(extra);
                startActivity(next);
            }
        });

    }

    /**
     * resume the activity and update the listview
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();

        mParkingLocationDB.getReadableDatabase();
        myArrayAdapter.notifyDataSetChanged();
        myList = mParkingLocationDB.getAllDataByUName(username);
        myArrayAdapter = new ArrayAdapter<MySavedParkingLocation>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, myList);
        mySavedListView.setAdapter(myArrayAdapter);
        if (myList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "EMPTY LIST",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
