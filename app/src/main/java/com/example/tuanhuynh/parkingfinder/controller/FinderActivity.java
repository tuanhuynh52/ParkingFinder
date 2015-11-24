package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.LocationAddress;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.LocationInfo;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.User;

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
    private String address = "";
    public List<LocationInfo> locationList;
    public ListView mListView;
    public ArrayAdapter<LocationInfo> locationAdapter;
    private int numberOfLocation;
    public LocationAddress locationAddress;
    public String api_url;
    public double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String uName = intent.getStringExtra("Username");
        String welcomeString = "Welcome, " + uName;
        getSupportActionBar().setTitle(welcomeString);

        Log.i(TAG, "On create called");

        View b = findViewById(R.id.customButton);
        b.setVisibility(View.GONE);

        EditText searchEditText = (EditText) findViewById(R.id.search_EditText);


        /*
        retrieve search button actions
         */
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass address to location class
                EditText search = (EditText) findViewById(R.id.search_EditText);
                address = search.getText().toString();
                LocationAddress.setAddress(address);
                if (locationList != null) {
                    locationList.clear();
                }

                if (address.equals("")) {
                    Toast.makeText(FinderActivity.this, "PLease enter your address!",
                            Toast.LENGTH_LONG).show();
                } else {
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
                        View b = findViewById(R.id.customButton);
                        b.setVisibility(View.GONE);
                        Log.i(TAG, "connected");
                    } else {
                        Log.i(TAG, "not connected");
                    }

                /*
                Retrieve data to listview
                 */
                    showListView();
                    /*
                    itemClick listener for each item in the list view
                     */
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(FinderActivity.this, ParkingLocation.class);
                            api_url = locationList.get(position).getUrl_api();
                            intent.putExtra("key_api_url", api_url);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

        /*
        Custom search button actions if and only if search button gives null results
         */
        Button customSearch = (Button) findViewById(R.id.customButton);
        customSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.equals("")) {
                    Toast.makeText(FinderActivity.this, "PLease enter your address!",
                            Toast.LENGTH_LONG).show();

                } else if (LocationAddress.getNumOfLocations() > 0) {
                    Toast.makeText(FinderActivity.this, "This action is only available if no parking locations found!!",
                            Toast.LENGTH_LONG).show();
                } else { //open custom search activity
                    Intent newIntent = new Intent(FinderActivity.this, CustomSearchActivity.class);
                    Bundle b = new Bundle();
                    b.putDouble("key_lat", lat);
                    b.putDouble("key_lng", lng);
                    newIntent.putExtras(b);
                    startActivity(newIntent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * shows list view of available locations
     */
    private void showListView(){
        mListView = (ListView)findViewById(R.id.listView);
        locationList = LocationAddress.ITEMS;
        locationAdapter = new ArrayAdapter<LocationInfo>(FinderActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, locationList);
        if(numberOfLocation> 0){
            mListView.setAdapter(locationAdapter);
        } else {
            mListView.setAdapter(null);
        }
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
                return JSONAddressUrl.getJSON();
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

            locationAddress = new LocationAddress();
            //JSON parser
            try {
                locationList.clear();
                LocationAddress.ITEMS.clear();
                JSONObject parentObj = new JSONObject(result);
                lat = parentObj.getDouble("lat");
                lng = parentObj.getDouble("lng");
                //Log.d(TAG, "lat is " + lat + '\n' + "long is: " + lng);
                //get number of parking locations if null throw a toast
                numberOfLocation = parentObj.getInt("locations");
                //passing data of number of locations to LocationAddress class
                LocationAddress.setNumOfLocations(numberOfLocation);
                //Log.d(TAG, "number of location: " + LocationAddress.getNumOfLocations());
                if (numberOfLocation == 0){
                    Toast.makeText(FinderActivity.this, "No Available Parking Spots Here" + "\n" +
                            "Please press custom search button",
                            Toast.LENGTH_LONG).show();
                    Button customSearch = (Button) findViewById(R.id.customButton);
                    customSearch.setVisibility(View.VISIBLE);
                    locationList.clear();
                    LocationAddress.ITEMS.clear();

                } else {
                    JSONArray locationArray = parentObj.getJSONArray("parking_listings");
                    for (int i=0; i<locationArray.length(); i++){
                        JSONObject location = locationArray.getJSONObject(i);
                        api_url = location.getString("api_url");
                        //add api url to a list to store a particular parking location information
                        String location_name = location.getString("location_name");
                        int distance = location.getInt("distance");
                        String formatPrice = location.getString("price_formatted");

                        LocationAddress.ITEMS.add(new LocationInfo(location_name, distance, formatPrice, api_url));
                    }
                    locationList = LocationAddress.ITEMS;
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
