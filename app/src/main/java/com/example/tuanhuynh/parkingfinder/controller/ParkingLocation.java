package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.LocationAddress;

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

public class ParkingLocation extends AppCompatActivity {

    private TextView locationName, address, startTime, endTime,
            availableSpot, price, directions, description;

    private static final String TAG = "ParkingLocation";

    private static final String KEY = "477e53144a5e5caa675d2db2768b7782";

    private String url_api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_location_menu);

        Intent intent = getIntent();
        url_api = intent.getStringExtra("key_api_url");

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networtInfo = connectivityManager.getActiveNetworkInfo();
        if (networtInfo != null && networtInfo.isConnected()) {
            GetUrlApiTask task = new GetUrlApiTask();
            task.execute();
            Log.i(TAG, "connected");
        } else {
            Log.i(TAG, "not connected");
        }

    }

    public String getUrlApi() throws IOException {

        URL url;
        HttpURLConnection conn = null;

        //my url with key to search a specicfic location

        String myApiUrl = url_api + "&key="+KEY;
        Log.d(TAG, myApiUrl);

        InputStream is = null;

        try {
            url = new URL(myApiUrl);
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

    private class GetUrlApiTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
//          params comes from the execute() call: params[0] is the url.
            try {
                return getUrlApi();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String urlResult) {
            super.onPostExecute(urlResult);
            //JSON parser
            Log.d(TAG, urlResult);
            try{
                JSONObject jsonObject = new JSONObject(urlResult);
                String name = jsonObject.getString("location_name");
                locationName = (TextView)findViewById(R.id.locationNameTV);
                locationName.setText(name);

                String l_address = jsonObject.getString("address");
                String city = jsonObject.getString("city");
                String state = jsonObject.getString("state");
                String zip = jsonObject.getString("zip");
                address = (TextView)findViewById(R.id.addressTV);
                address.setText(l_address+" "+ city +" "+ state +" "+ zip);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
