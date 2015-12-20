package com.cap.projet5iss;

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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    }


}