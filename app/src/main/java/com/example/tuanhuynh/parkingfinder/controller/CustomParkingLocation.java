package com.example.tuanhuynh.parkingfinder.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.DestinationDatabase.ParkingLocationDB;

/**
 * details of locations searched customly by user
 */
public class CustomParkingLocation extends AppCompatActivity {

    private ParkingLocationDB parkingLocationDB;
    private String locationName, address, mySavedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_parking_location);

        parkingLocationDB = new ParkingLocationDB(this);

        Bundle b = getIntent().getExtras();
        locationName = b.getString("name_key");
        address = b.getString("address_key");
        int distance = b.getInt("distance_key");
        //convert ft to mile
        String distanceInMile = String.format("%.1f", distance / 5280.00);
        mySavedUsername = b.getString("username");

        TextView name = (TextView) findViewById(R.id.nameTextView);
        name.setText(locationName);
        TextView addressTv = (TextView) findViewById(R.id.addressTextView);
        addressTv.setText(address);
        TextView distanceTV = (TextView) findViewById(R.id.distanceTextView);
        distanceTV.setText(String.valueOf(distanceInMile) + " Mile");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_custom_parking_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_custom:
                //add details of location into MyPlaceDatabase
                String p_type = "Unknown";
                String price_formatted = "Unknown";
                String p_description = "Unknown";
                if (!parkingLocationDB.isDataExisted(mySavedUsername, locationName)) {
                    parkingLocationDB.addData(mySavedUsername, locationName, address,
                            p_type, price_formatted, p_description);
                    Toast.makeText(CustomParkingLocation.this, "Added to " + mySavedUsername,
                            Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(CustomParkingLocation.this, "This location is already saved",
                            Toast.LENGTH_SHORT).show();
                }
                parkingLocationDB.closeDB();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
