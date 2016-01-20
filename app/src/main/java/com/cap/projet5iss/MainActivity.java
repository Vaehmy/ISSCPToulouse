package com.cap.projet5iss;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.cap.projet5iss.handlers.JSONHandler;
import com.cap.projet5iss.handlers.VehiclesHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private Switch switchUD;
    private ImageButton bRoutes;
    private JSONHandler jHAllDrivers;
    private ArrayList aLAllDrivers; // Array with all the Driver's JSONObjects
    private VehiclesHandler allVehiHandler;
    String jsonFileName;
    String dataToPost ;
    private TextView tv_welcome ;
    private String serverLocation = "http://10.32.1.8:8080/SmartCitiesProject/rest/UserService/";


    protected void onStart() {
        super.onStart();
    }

    /**
     * Called when the application is launched
     *
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

       /* lv = (ListView) findViewById(R.id.listView);
        sv = (SearchView) findViewById(R.id.searchView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adress);
        lv.setAdapter(adapter);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });*/

        JSONObject obj=new JSONObject();

        try {
            obj.put("name", "Test JSON");
            obj.put("addStreet", "FakeStreet for JSON Test");
            obj.put("idVehicule", 10);
            obj.put("isDriver", true);
            obj.put("name", "Test JSON");
            obj.put("sexe", "M");
            obj.put("telephone", 0610120304);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataToPost = obj.toString() ;
        new HttpAsyncPOSTTask().execute(serverLocation+"/post/users");

    }

    /**
     * Initialize the views and the handlers
     */
    public void initialize() {

        // Views
        switchUD = (Switch) findViewById(R.id.switchUD);
        bRoutes = (ImageButton) findViewById(R.id.ib_route);
        tv_welcome = (TextView) findViewById(R.id.tV_welcomeName);



        // JSON Handlers
        jHAllDrivers = new JSONHandler(getApplicationContext(), "allDrivers.JSON");
    }

    /**
     * Set all the listeners
     * TODO
     * - Add a listener for the Profile Button
     */
    public void setListeners() {
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

        bRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(Routes.class);
            }
        });
    }

    public void startNewActivity(Class activityToStart){
        Intent intent = new Intent(this, activityToStart);
        startActivity(intent);
    }
    /**
     * Called once the Google Map is ready
     * Before the map is ready, "mMAp" is null and cannot be used
     *
     * @param googleMap The google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Centers the map on Toulouse
        LatLng toulouseWilson = new LatLng(43.6050, 1.447735);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toulouseWilson, 15));

    }

    public void loadDriverMode() {
        bRoutes.setVisibility(View.VISIBLE);
    }

    /**
     * Load the passenger mode:
     * - check the current location of all drivers
     * - adds the markers on the map
     */

    public void loadPassengerMode() {

        bRoutes.setVisibility(View.GONE);
        JSONArray jAallVehicules;

        try {
            // Parse JSON files
            readAllDrivers();

            // Sets the vehicle JSONArray
            jAallVehicules = allVehiHandler.getJSONArray_allVehicles();

            // Browse the JSONArray of Vehicles and add a marker for each one of them
            for (int i = 0; i < jAallVehicules.length(); i++) {
                LatLng location;

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

    public void loadAllRoutes() {
        JSONArray jDepartArriveRoutes;
    }



        public void readAllDrivers() throws JSONException {

        // Parsing all drivers
        aLAllDrivers = jHAllDrivers.jsonFile2ArrayList(getApplicationContext(), "allDrivers.json");
        // Parsing all vehicles
        allVehiHandler = jHAllDrivers.getAllDriversHandler().getVehiclesHandler();


            jsonFileName = "allDrivers.json";
            Log.i("MainActivity", "Going to GET all drivers ... ");
            new HttpAsyncGETTask().execute(serverLocation + "drivers");

            //  Log.i("MainActivity", "Going to GET all users ... ");
            //  jsonFileName = "allUsers.json";
            //   new HttpAsyncGETTask().execute(serverLocation+"users");

            //  Log.i("MainActivity", "Going to GET user 1 ... ");
            // jsonFileName = "user1.json";
            //  new HttpAsyncGETTask().execute(serverLocation+"users/1");

            //   jsonFileName = "allPassengers.json";
            //   Log.i("MainActivity", "Going to GET all passengers ... ");
            //   new HttpAsyncGETTask().execute(serverLocation+"passengers");

            //    jsonFileName = "allVehicles.json";
            //   Log.i("MainActivity", "Going to GET all cars ... ");
            //   new HttpAsyncGETTask().execute(serverLocation+"cars");

            //    jsonFileName = "allRoutes.json";
            //    Log.i("MainActivity", "Going to GET all routes ... ");
            //    new HttpAsyncGETTask().execute(serverLocation + "routes");




    }


    /**
     * To check is the smartphone is connected to the network
     *
     * @return
     */
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }



    //*************************************************************************************


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
        Log.i("MAINACTIVITY", "Got following JSON : " + result);
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
     * We call the GET method in an AsyncTask ( = a separate thread )
     * so that the application will not freeze while getting the JSON files
     */
    private class HttpAsyncGETTask extends AsyncTask<String, Void, String> {

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

    /***********************************************
     *               JSON POST
     **********************************************/


    private class HttpAsyncPOSTTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.i("JSON POST REQUEST", "Data Sent");
        }
    }


    public String POST(String urlToPost) {

        BufferedReader reader = null;

        // Send data

        // Defined URL  where to send data
        URL url = null;
        try {
            url = new URL(urlToPost);

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(dataToPost);
            wr.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "POST";
    }



    //*************************************************************************************


    //  INDICATION ON HOW TO USE GET AND POST REST REQUEST



    /**
     *
     * Example steps to get all the drivers' json file from the network :
     *
     * 1. Set a correct name for the file where we will store the json data
     * 2. Call the AsynTask to perform the operation on separate thread with the correct REST url
     *
     *      jsonFileName = "theFileNameINeed.json" ;
     *      new HttpAsyncGETTask().execute( "myREST_GET_URL" );
     *
    */


    /**
     *
     * Example steps for a POST request :
     *
     * 1. Form the correct string according to the json you need to post
     * 2. Save this string in dataToPost
     * 3. Call the asyncTask with the correct POST url
     *
     *     dataToPost = "myJSONString" ;
     *     new HttpAsynPOSTTask().execute( "myREST_POST_URL" );
     */


   }