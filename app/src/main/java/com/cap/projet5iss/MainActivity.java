package com.cap.projet5iss;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.cap.projet5iss.handlers.*;
import com.cap.projet5iss.handlers.JSONHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    UserLocalStore userLocalStore;
    private GoogleMap mMap;
    private Switch switchUD ;
    private JSONHandler jHAllDrivers ;
    private ArrayList aLAllDrivers ;
    private VehiclesHandler allVehiHandler ;

    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setListeners();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        userLocalStore= new UserLocalStore(this);

    }

    public void initialize(){

        // Views
        switchUD = (Switch) findViewById(R.id.switchUD);

        // JSON Handlers
        jHAllDrivers = new JSONHandler(getApplicationContext(),"allDrivers.JSON");
    }

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

                // Adding a marker corresponding to this location
                mMap.addMarker(new MarkerOptions().position(location));
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
    }




}