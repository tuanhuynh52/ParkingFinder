package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.LocationAddress;
import com.example.tuanhuynh.parkingfinder.model.LocationInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class CustomSearchActivity extends AppCompatActivity {

    private static final String KEY = "477e53144a5e5caa675d2db2768b7782";

    private TextView addressTV;
    private String storedAddress;
    private int distance;
    private static double lat, lng;
    private static final String TAG = "Customer Search";

    private List<LocationInfo> locationList;
    private ListView mListView;
    private ArrayAdapter<LocationInfo> locationAdapter;
    //location name, address,
    private String locationName, addressToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_search);

        Bundle b = getIntent().getExtras();
        lat = b.getDouble("key_lat");
        lng = b.getDouble("key_lng");

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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newIntent = new Intent(CustomSearchActivity.this, CustomParkingLocation.class);
                Bundle b = new Bundle();
                locationName = locationList.get(position).getLocation_name();
                addressToShow = locationList.get(position).getAddressToShow();
                distance = locationList.get(position).distance;

                b.putString("name_key", locationName);
                b.putString("address_key", addressToShow);
                b.putInt("distance_key", distance);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });

    }


    public static String getUrlJSON() throws IOException {


        URL url;
        HttpURLConnection conn = null;

        //my url with key to search a specicfic location

        String myLatLngUrl = "http://api.parkwhiz.com/venue/search/?lat="+lat+"&lng="+lng+"&key="+KEY;
        Log.d("JSONLatLng", myLatLngUrl);
        InputStream is = null;

        try {
            url = new URL(myLatLngUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String strContent = getStringFromInputStream(is);
            return strContent;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return null;

    }

    /**
     * Gets string from inputstream
     * @param is inputStream
     * @return string of inputstream
     */
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String str;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null){
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private class GetLngLatTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
//          params comes from the execute() call: params[0] is the url.
            try {
                return getUrlJSON();
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
                    locationName = locationObject.getString("name");
                    distance = locationObject.getInt("distance");
                    //get additional info for custom parking location
                    String address = locationObject.getString("address");
                    String city = locationObject.getString("city");
                    String state = locationObject.getString("state");
                    String zip = locationObject.getString("zip");
                    addressToShow = address + ", " + city + ", " + state + " " + zip;
                    LocationAddress.ITEMS.add(new LocationInfo(locationName, addressToShow, distance));
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
