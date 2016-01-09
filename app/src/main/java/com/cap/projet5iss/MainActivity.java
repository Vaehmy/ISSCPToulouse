package com.cap.projet5iss;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity; 
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.cap.projet5iss.handlers.*;
import com.cap.projet5iss.handlers.JSONHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import static android.os.Environment.*;

/**
 * NOTES
 * Toute la partie Parser et Calculs de localisation, il faudrait
 * le faire en "AsynTask" de façon à ne pas bloquer l'application si
 * les algorithmes commencent à prendre trop de temps. Pour l'instant c'est instantanné,
 * mais dans le cas d'une grande BD on risquerait d'avoir quelques soucis côté performances.
 */

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    UserLocalStore userLocalStore;
    private GoogleMap mMap;
    private Switch switchUD ;
    private JSONHandler jHAllDrivers ;
    private ArrayList aLAllDrivers ; // Array with all the Driver's JSONObjects
    private VehiclesHandler allVehiHandler ;
    String jsonFileName;
    File jsonFile;

    protected void onStart(){
        super.onStart();
    }

    /**
     * Called when the application is launched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setListeners();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        jsonFileName = "blankFile.json";

        // Creating the directory for our application if it does not already exist
        File file = new File(Environment.getExternalStorageDirectory(), "/DCToulouse/");
        if (!file.exists()) {
            file.mkdirs();
        }
        userLocalStore= new UserLocalStore(this);

    }

    /**
     * Initialize the views and the handlers
     */
    public void initialize(){

        // Views
        switchUD = (Switch) findViewById(R.id.switchUD);

        // JSON Handlers
        jHAllDrivers = new JSONHandler(getApplicationContext(),"allDrivers.JSON");
    }

    /**
     * Set all the listeners
     * TODO
     * - Add a listener for the Profile Button
     * - Add a listener for the Edit Routes Buttons
     */
    public void setListeners(){
        switchUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchUD.isChecked()) {
                    Log.i("MAINACTIVITY", "Just switched to DRIVER mode");
                    loadDriverMode();
                } else {
                    Log.i("MAINACTIVITY", "Just switched to PASSENGER mode");
                    loadPassengerMode();
                }
            }
        });
    }

    /**
     * Called once the Google Map is ready
     * Before the map is ready, "mMAp" is null and cannot be used
     * @param googleMap The google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Centers the map on Toulouse
        LatLng toulouseWilson = new LatLng(43.6050, 1.447735);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toulouseWilson, 15));

    }

    public void loadDriverMode(){
        //TODO
    }

    /**
     * Load the passenger mode:
     * - check the current location of all drivers
     * - adds the markers on the map
     */
    public void loadPassengerMode(){

        JSONArray jAallVehicules;

        try {
            // Parse JSON files
            readAllDrivers();

            // Sets the vehicle JSONArray
            jAallVehicules = allVehiHandler.getJSONArray_allVehicles();

            // Browse the JSONArray of Vehicles and add a marker for each one of them
            for (int i = 0; i < jAallVehicules.length(); i++) {
                LatLng location ;

                // Retrieving the location for a given vehicle
                location = allVehiHandler.getLatLongFromObj(jAallVehicules.getJSONObject(i));

                JSONObject driverObj;
                driverObj = jHAllDrivers.getAllDriversHandler()
                        .findDriverFromVehicleId(String.valueOf(jAallVehicules
                                        .getJSONObject(i).getInt("idVehicle")));

                // Adding a marker corresponding to this location
                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                        .snippet("Tel : " + driverObj.getString("telephone"))
                        .title(driverObj.getString("name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void readAllDrivers() throws JSONException {

        // Parsing all drivers
        aLAllDrivers = jHAllDrivers.jsonFile2ArrayList(getApplicationContext(), "allDrivers.json");
        // Parsing all vehicles
        allVehiHandler = jHAllDrivers.getAllDriversHandler().getVehiclesHandler();

        /**
         * Exemple steps to get all the drivers' json file from the network :
         * 1. Set a correct name for the file where we will store the json data
         * 2. Call AsynTask to perform network operation on separate thread
         *    with the correct REST url
         */

        jsonFileName = "allDrivers.json";
        new HttpAsyncTask().execute("http://hmkcode.appspot.com/rest/controller/get.json");
    }


    /***********************************************
     *               JSON GETTER
     **********************************************/

    public String GET(String url){
        InputStream inputStream = null;
        String result = "";

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null){
                // Store inputstream in local file
                copyInputStreamToFile(inputStream);
                result = "Worked!";
            }
            else {
                result = "Did not work!";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Method to copy an inputstream into a file with the correct json name
     * @param in
     */
    private void copyInputStreamToFile( InputStream in ) {
        try {
            Log.i("MainActivity", "Creating " + jsonFileName + " in " + getExternalStorageDirectory()+"/DCToulouse");
            File file = new File (getExternalStorageDirectory()+"/DCToulouse", jsonFileName);
            Log.i("MainActivity", "File was successfully created. Starting to write into " + jsonFileName + "...");
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
            Log.i("MainActivity", "File was successfully written. All streams are closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * To check is the smartphone is connected to the network
     * @return
     */
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    /**
     * We call the GET method in an AsyncTask ( = a separate thread )
     * so that the application will not freeze while getting the JSON files
     */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.i("JSON GET REQUEST", "Got a result");
        }
    }

}