package com.cap.projet5iss.handlers;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nana on 20/12/2015.
 */
public class AllDriversHandler {

    JSONObject json ;
    JSONArray jsonArray ;
    VehiclesHandler vehiclesHandler ;
    Context context ;

    public AllDriversHandler(JSONObject json, Context context) {
        this.json = json;
        this.context = context ;
        this.vehiclesHandler = new VehiclesHandler(context);
    }

    public ArrayList allDriversfromJSON(JSONObject json) {

        try {
            jsonArray = this.json.getJSONArray("allDrivers");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
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

    public JSONArray getJsonArray(){
        return jsonArray ;
    }

    /*public LatLng getDriverLatLong(JSONObject jsonObject){

        Double lat ;
        Double lng ;

        return new LatLng(lat, lng);
    }*/
}
