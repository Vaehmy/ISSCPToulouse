package com.cap.projet5iss.handlers;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nana on 20/12/2015.
 */
public class AllDriversHandler {

    JSONObject json ;
    JSONArray jAAllDrivers;
    VehiclesHandler vehiclesHandler ;
    RoutesHandler routesHandler ;
    Context context ;

    public AllDriversHandler(JSONObject json, Context context) {
        this.json = json;
        this.context = context ;
        this.vehiclesHandler = new VehiclesHandler(context);
        this.routesHandler = new RoutesHandler(context);
    }

    public ArrayList allDriversfromJSON(JSONObject json) {

        try {
            jAAllDrivers = this.json.getJSONArray("allDrivers");

            for (int i = 0; i < jAAllDrivers.length(); i++) {
                JSONObject jsonObject = jAAllDrivers.getJSONObject(i);
                String name = jsonObject.getString("name");
                String vehicule = jsonObject.getString("idVehicle");

                Log.i("PARSING...", name + " for vehicule #" + vehicule);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return new ArrayList();
    }

    public VehiclesHandler getVehiclesHandler() {
        return vehiclesHandler;
    }

    public RoutesHandler getRoutesHandler() {
        return routesHandler;
    }

    public JSONArray getjAAllDrivers(){
        return jAAllDrivers;
    }

    /*public LatLng getDriverLatLong(JSONObject jsonObject){

        Double lat ;
        Double lng ;

        return new LatLng(lat, lng);
    }*/

    public JSONObject findDriverFromVehicleId(String idVehicle) throws JSONException {
        Boolean found = false ;
        int j = 0;
        for (int i = 0; i < jAAllDrivers.length(); i++) {
                if (jAAllDrivers.getJSONObject(i).getString("idVehicle").equals(idVehicle)){
                    found = true ;
                    j = i ;
                    break;
                }
        }
        return jAAllDrivers.getJSONObject(j);
    }
}
