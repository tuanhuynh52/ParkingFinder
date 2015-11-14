package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.DestinationInfo;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.Location;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.LocationInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * this class shows the UI of search activity for user that allows user to search nearby
 * parkings by entering a specific address of destination
 *
 */
public class FinderActivity extends AppCompatActivity {

    private static final String TAG = "FinderActivity";
    private Button searchButton;
    public List<LocationInfo> locationList;
    public ListView mListView;
    public ArrayAdapter<LocationInfo> locationAdapter;
    private int numberOfLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(TAG, "On create called");


        /*
        retrieve search button actions
         */
        searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass address to location class
                EditText search = (EditText) findViewById(R.id.search_EditText);
                String address = search.getText().toString();
                if(locationList != null){
                    locationList.clear();
                }
                if (address.equals("")){
                    Toast.makeText(FinderActivity.this, "PLease enter your address!",
                            Toast.LENGTH_LONG).show();

                }
                else {
                    Location.setAddress(address);
                /*
                * manging connection from the application to networking service
                * before attempting to fetch url, make sure there is a network connection
                */
                    ConnectivityManager connectivityManager = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networtInfo = connectivityManager.getActiveNetworkInfo();
                    if (networtInfo != null && networtInfo.isConnected()) {
                        GetAddressTask task = new GetAddressTask();
                        task.execute();
                        Log.i(TAG, "connected");
                    } else {
                        Log.i(TAG, "not connected");
                    }

                /*
                Retrieve data to listview
                 */
                    showListView();
                }
            }
        });

    }

    /**
     * shows list view of available locations
     */
    private void showListView(){
        mListView = (ListView)findViewById(R.id.listView);
        locationList = Location.ITEMS;
        locationAdapter = new ArrayAdapter<LocationInfo>(FinderActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, locationList);
        if(numberOfLocation> 0){
            mListView.setAdapter(locationAdapter);
        } else {
            mListView.setAdapter(null);
        }
        locationList.clear();
        locationAdapter.clear();
    }

    /**
     *Uses AsyncTask to create a task away from the main UI thread. This task takes a
     * URL string and uses it to create an HttpUrlConnection. Once the connection
     * has been established, the AsyncTask downloads the contents of the webpage as
     * an InputStream. Finally, the InputStream is converted into a string, which is
     * displayed in the UI by the AsyncTask's onPostExecute method.
     */
    private class GetAddressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
//          params comes from the execute() call: params[0] is the url.
            try {
                return AddressUrl.getJSON();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        /**
         * onPostExecute displays the results of the AsyncTask after getting JSON data from url
         * @param result all nearby location information displayed to string
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //JSON parser
            try {
                locationList.clear();
                Location.ITEMS.clear();
                JSONObject parentObj = new JSONObject(result);
                double lat = parentObj.getDouble("lat");
                double lng = parentObj.getDouble("lng");
                Log.d(TAG, "lat is "+ lat+ '\n'+ "long is: "+lng);
                DestinationInfo.setLat(lat);
                DestinationInfo.setLng(lng);
                //get number of parking locations if null throw a toast
                numberOfLocation = parentObj.getInt("locations");
                //passing data of number of locations to Location class
                Location.setNumOfLocations(numberOfLocation);
                Log.d(TAG, "number of location: " + Location.getNumOfLocations());
                if (numberOfLocation == 0){
                    Toast.makeText(FinderActivity.this, "No Available Parking Spots Here",
                            Toast.LENGTH_LONG).show();
                    locationList.clear();
                    Location.ITEMS.clear();

                } else {
                    JSONArray locationArray = parentObj.getJSONArray("parking_listings");
                    for (int i=0; i<locationArray.length(); i++){
                        JSONObject location = locationArray.getJSONObject(i);
                        String location_name = location.getString("location_name");
                        int distance = location.getInt("distance");
                        String formatPrice = location.getString("price_formatted");

                        LocationInfo locationInfo =
                                new LocationInfo(location_name, distance, formatPrice);
                        Location.ITEMS.add(locationInfo);
                    }
                    locationList = Location.ITEMS;
                    //sort location by shortest distance
                    Collections.sort(locationList);
                    mListView.setAdapter(locationAdapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
