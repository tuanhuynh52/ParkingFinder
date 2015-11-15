package com.example.tuanhuynh.parkingfinder.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.DestinationInfo;

public class ParkingLocationMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_location_menu);

        TextView destinationTextView = (TextView)findViewById(R.id.destinationTextView);
        destinationTextView.setText(DestinationInfo.destinationInfoList.toString());
    }


}
