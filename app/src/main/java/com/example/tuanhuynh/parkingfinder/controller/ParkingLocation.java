package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.DestinationDatabase.ParkingLocationDB;

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

public class ParkingLocation extends AppCompatActivity {

    private TextView locationName, address, type,  startTime, endTime,
            availableSpot, price, directions, description;

    private static final String TAG = "ParkingLocation";

    private static final String KEY = "477e53144a5e5caa675d2db2768b7782";

    private String name, fullAddress, p_type, p_description, price_formatted, url_api;

    public ParkingLocationDB mParkingLocationDB;


    private String mySavedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_location_menu);
        setTitle("Parking Destination");
        mParkingLocationDB = new ParkingLocationDB(this);

        locationName = (TextView)findViewById(R.id.locationNameTV);
        address = (TextView)findViewById(R.id.addressTV);
        type = (TextView)findViewById(R.id.typeTV);
        availableSpot = (TextView)findViewById(R.id.spotTV);
        price = (TextView)findViewById(R.id.priceTv);
        startTime = (TextView)findViewById(R.id.startTimeTV);
        endTime = (TextView)findViewById(R.id.endTimeTV);

        directions = (TextView)findViewById(R.id.directionTV);
        directions.setMovementMethod(new ScrollingMovementMethod());

        description = (TextView)findViewById(R.id.descriptionTV);
        description.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        url_api = intent.getStringExtra("key_api_url");
        mySavedUsername = intent.getStringExtra("username");
        Log.d(TAG, "username is "+ mySavedUsername);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parking_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                //add details of location into MyPlaceDatabase
                if(!mParkingLocationDB.isDataExisted(mySavedUsername, name)){
                    mParkingLocationDB.addData(mySavedUsername, name, fullAddress,
                            p_type, price_formatted, p_description);
                    Toast.makeText(ParkingLocation.this, "Added to "+mySavedUsername,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    Toast.makeText(ParkingLocation.this, "This location is already saved",
                            Toast.LENGTH_SHORT).show();
                }
                mParkingLocationDB.closeDB();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * get string api from the internet using url api and my own key
     * @return string content
     * @throws IOException io exception
     */
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
            String str = getStringFromInputStream(is);
            return str;

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
            //Log.d(TAG, urlResult);
            try{
                JSONObject jsonObject = new JSONObject(urlResult);
                name = jsonObject.getString("location_name");
                locationName.setText(name);

                String l_address = jsonObject.getString("address");
                String city = jsonObject.getString("city");
                String state = jsonObject.getString("state");
                String zip = jsonObject.getString("zip");
                fullAddress = l_address + " " + city + ", " + state + " " + zip;
                address.setText(fullAddress);

                p_type = jsonObject.getString("type");
                type.setText(p_type);

                String p_directions = jsonObject.getString("directions");
                String str = "<br />";
                p_directions = p_directions.replaceAll(str, "");
                directions.setText(p_directions);

                p_description = jsonObject.getString("description");
                description.setText(p_description);

                JSONArray jsonArray = jsonObject.getJSONArray("listings");
                for (int i=0; i<jsonArray.length();i++){
                    JSONObject listObject = jsonArray.getJSONObject(i);
                    int avaiSpot = listObject.getInt("available_spots");
                    availableSpot.setText(String.valueOf(avaiSpot));

                    price_formatted = listObject.getString("price_formatted");
                    price.setText(price_formatted);

                    String start_time_formatted = listObject.getString("start_utc");
                    startTime.setText(start_time_formatted);

                    String end_time_formatted = listObject.getString("end_utc");
                    endTime.setText(end_time_formatted);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
