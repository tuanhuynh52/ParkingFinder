package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tuanhuynh.parkingfinder.JSONParser;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.Location;
import com.example.tuanhuynh.parkingfinder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * this class shows the UI of search activity for user that allows user to search nearby
 * parkings by entering a specific address of destination
 *
 */
public class FinderActivity extends AppCompatActivity {

    private static final String TAG = "FinderActivity";
    private Button searchButton;
    private List<Location.LocationInfo> locationList;
    public ListView mListView;
    public ArrayAdapter<Location.LocationInfo> locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(TAG, "On create called");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
        retrieve search button actions
         */
        searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * manging connection from the application to networking service
                * before attempting to fetch url, make sure there is a network connection
                */
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networtInfo = connectivityManager.getActiveNetworkInfo();
                if(networtInfo != null && networtInfo.isConnected()){
                    DownloadWebpageTask task = new DownloadWebpageTask();
                    task.execute();
                    Log.i(TAG, "connected");
                } else {
                    Log.i(TAG, "not connected");
                }
                /*
                Retrieve data to listview
                 */
                mListView = (ListView)findViewById(R.id.listView);

                locationList = Location.ITEMS;

                //locationAdapter = new ArrayAdapter<Location.LocationInfo>(locationList);

            }
        });

    }

    /**
     *Uses AsyncTask to create a task away from the main UI thread. This task takes a
     * URL string and uses it to create an HttpUrlConnection. Once the connection
     * has been established, the AsyncTask downloads the contents of the webpage as
     * an InputStream. Finally, the InputStream is converted into a string, which is
     * displayed in the UI by the AsyncTask's onPostExecute method.
     */
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            JSONParser jsonParser = new JSONParser();
//             params comes from the execute() call: params[0] is the url.
//            Location location = new Location();
            try {
                return jsonParser.getJSON();
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

//            TextView textView = (TextView)findViewById(R.id.textView);
//            textView.setText(result);

            try {
                locationList.clear();
                Location.ITEMS.clear();
                JSONArray locationArray = new JSONArray(result);

                for (int i=0; i<locationArray.length(); i++){
                    JSONObject location = locationArray.getJSONObject(i);

                    String location_name = location.getString("name");
                    String address = location.getString("address");
                    String city = location.getString("city");
                    String state = location.getString("state");
                    String zip = location.getString("zip");
                    int distance = location.getInt("distance");

                    Location.ITEMS.add(new Location.LocationInfo(location_name, distance));
                }

                locationList = Location.ITEMS;
                mListView.setAdapter(locationAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
