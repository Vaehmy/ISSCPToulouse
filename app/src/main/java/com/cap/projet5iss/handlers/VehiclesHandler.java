package com.cap.projet5iss.handlers;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nana on 20/12/2015.
 */
public class VehiclesHandler {

    JSONArray jAallVehicles;
    Context context;

    public VehiclesHandler(Context context) {
        this.context = context;
    }

    public JSONObject getJSON() throws JSONException {
        String json = null;
        try {
            InputStream is = context.getResources().getAssets().open("allVehi.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        JSONObject jsonO = new JSONObject(json);
        jAallVehicles = jsonO.getJSONArray("allVehicles");
        return jsonO;
    }

    public JSONArray getJSONArray_allVehicles() throws JSONException {

        return getJSON().getJSONArray("allVehicles");
    }

    public JSONObject findVehicleFromVehicleId(String idVehicle) throws JSONException {
        Boolean found = false ;
        int j = 0;
        for (int i = 0; i < jAallVehicles.length(); i++) {
            if (jAallVehicles.getJSONObject(i).getString("idVehicle").equals(idVehicle)){
                found = true ;
                j = i ;
                break;
            }
        }
        return jAallVehicles.getJSONObject(j);
    }

    public LatLng getLatLongFromObj(JSONObject jsonObject) throws JSONException {
        Double lat ;
        Double lng ;
        lat = jsonObject.getDouble("currentLat");
        lng = jsonObject.getDouble("currentLong");
        LatLng loc = new LatLng(lat,lng);
        return loc ;
    }

}