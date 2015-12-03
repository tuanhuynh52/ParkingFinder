package com.example.tuanhuynh.parkingfinder.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.DestinationDatabase.ParkingLocationDB;

/**
 * Show detail of a location in saved list
 */
public class MyLocationDetail extends AppCompatActivity {

    private static final String TAG = "Detail activity";
    private TextView l_name, l_address, l_type, l_price, l_description;
    private String uName, locationName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location_detail);

        setTitle("DETAIL");

        Bundle b = getIntent().getExtras();
        locationName = b.getString("extra_location");
        uName = b.getString("extra_name");
        address = b.getString("extra_address");
        String type = b.getString("extra_type");
        String price = b.getString("extra_price");
        String description = b.getString("extra_description");

        l_name = (TextView) findViewById(R.id.savedName);
        l_name.setText(locationName);

        l_address = (TextView) findViewById(R.id.savedAddress);
        l_address.setText(address);

        l_type = (TextView) findViewById(R.id.savedType);
        l_type.setText(type);

        l_price = (TextView) findViewById(R.id.savedPrice);
        l_price.setText(price);

        l_description = (TextView) findViewById(R.id.savedDescription);
        l_description.setMovementMethod(new ScrollingMovementMethod());
        l_description.setText(description);

        Button delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParkingLocationDB myDB = new ParkingLocationDB(v.getContext());
                myDB.deleteLocation(uName, locationName);
                Toast.makeText(getApplicationContext(), "This location has been deleted",
                        Toast.LENGTH_SHORT).show();
                myDB.closeDB();
                finish();
            }
        });
    }

}
