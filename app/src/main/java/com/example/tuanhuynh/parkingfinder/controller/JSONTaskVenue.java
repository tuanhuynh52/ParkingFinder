package com.example.tuanhuynh.parkingfinder.controller;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tuanhuynh.parkingfinder.model.UserDatabase.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tuan Huynh on 11/8/2015.
 */
public class JSONTaskVenue extends AsyncTask<String, Void, String> {

    public List<Location.LocationInfo> locationList;
    public ListView mListView;
    public ArrayAdapter<Location.LocationInfo> locationAdapter;

    @Override
    protected String doInBackground(String... urls) {
//          params comes from the execute() call: params[0] is the url.
        try {
            return LatLngUrl.getNearbyLocation();
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

        try {
            JSONArray locationArray = new JSONArray(result);
            for (int i=0; i<locationArray.length();i++){
                JSONObject jsonObject = locationArray.getJSONObject(i);
                String location_name = jsonObject.getString("name");
                int distance = jsonObject.getInt("distance");
                String formatPrice = "Unknown";

                Location.ITEMS.add(new Location.LocationInfo(location_name, distance, formatPrice));
            }
            locationList = Location.ITEMS;
            mListView.setAdapter(locationAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
