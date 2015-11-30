package com.example.tuanhuynh.parkingfinder.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.DestinationDatabase.MySavedParkingLocation;
import com.example.tuanhuynh.parkingfinder.model.LocationAddress;
import com.example.tuanhuynh.parkingfinder.model.LocationInfo;

import java.util.List;

public class MyDestination extends AppCompatActivity {

    private ListView mySavedListView;
    private List<MySavedParkingLocation> mySavedLocationList;
    private ArrayAdapter<MySavedParkingLocation> mySavedParkingLocationArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_destination);
        setTitle("My Saved Destinations");

        mySavedPlacesListView();

    }

    /**
     * show list view of saved parking locations
     */
    private void mySavedPlacesListView(){
        mySavedListView = (ListView)findViewById(R.id.MyPlaceListView);
        mySavedLocationList = MySavedParkingLocation.ITEMS;
        mySavedParkingLocationArrayAdapter = new ArrayAdapter<MySavedParkingLocation>
                (MyDestination.this,
                android.R.layout.simple_list_item_1,
                        android.R.id.text1, mySavedLocationList);
        mySavedListView.setAdapter(mySavedParkingLocationArrayAdapter);
    }

}
