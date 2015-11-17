package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.LocationAddress;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.LocationInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CustomSearchActivity extends AppCompatActivity {

    private TextView addressTV;
    private String storedAddress;
    private static final String TAG = "Customer Search";

    private List<LocationInfo> locationList;
    private ListView mListView;
    private ArrayAdapter<LocationInfo> locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_search);

        storedAddress = LocationAddress.getAddress();
        addressTV = (TextView)findViewById(R.id.addressCustom);
        addressTV.setText("Destination searched: " +'\n'+ storedAddress);

        if(locationList != null){
            locationList.clear();
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networtInfo = connectivityManager.getActiveNetworkInfo();
        if (networtInfo != null && networtInfo.isConnected()) {
            GetLngLatTask task = new GetLngLatTask();
            task.execute();
            Log.i(TAG, "connected");
        } else {
            Log.i(TAG, "not connected");
        }

        mListView = (ListView)findViewById(R.id.customListView);
        locationList = LocationAddress.ITEMS;
        locationAdapter = new ArrayAdapter<LocationInfo>(CustomSearchActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, locationList);
        mListView.setAdapter(locationAdapter);

    }

    private class GetLngLatTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
//          params comes from the execute() call: params[0] is the url.
            try {
                return JSONLngLatUrl.getUrlJSON();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String urlResult) {
            super.onPostExecute(urlResult);

            //lat and long URL JSON Parser
            try{
                locationList.clear();
                LocationAddress.ITEMS.clear();

                JSONArray locationArray = new JSONArray(urlResult);
                for(int i=0; i<locationArray.length();i++){
                    JSONObject locationObject = locationArray.getJSONObject(i);
                    String locationName = locationObject.getString("name");
                    int distance = locationObject.getInt("distance");
                    String price = "Not Available";

                    LocationAddress.ITEMS.add(new LocationInfo(locationName, distance, price));
                }
                locationList = LocationAddress.ITEMS;
                Collections.sort(locationList);
                mListView.setAdapter(locationAdapter);

            } catch (JSONException je){
                je.printStackTrace();
            }

        }
    }
}
