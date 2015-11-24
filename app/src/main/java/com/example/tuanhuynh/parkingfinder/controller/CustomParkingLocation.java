package com.example.tuanhuynh.parkingfinder.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tuanhuynh.parkingfinder.R;

public class CustomParkingLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_parking_location);

        Bundle b = getIntent().getExtras();
        String locationName = b.getString("name_key");
        String address = b.getString("address_key");
        int distance = b.getInt("distance_key");

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(locationName);
        TextView addressTv = (TextView)findViewById(R.id.addressTextView);
        addressTv.setText(address);
        TextView distanceTV = (TextView)findViewById(R.id.distanceTextView);
        distanceTV.setText(String.valueOf(distance) + " Ft");
    }
}
