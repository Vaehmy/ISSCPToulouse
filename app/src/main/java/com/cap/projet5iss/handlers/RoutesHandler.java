package com.cap.projet5iss.handlers;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nana on 21/01/2016.
 */
public class RoutesHandler {

    JSONArray jAallRoutes;
    Context context;

    public RoutesHandler(Context context) {
        this.context = context;
        jAallRoutes = getJSONArray_allRoutes();
    }

    public JSONObject getJSON(){
        String json = null;
        try {
            InputStream is = context.getResources().getAssets().open("AllRoutes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        JSONObject jsonO = null;
        try {
            jsonO = new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonO;
    }

    public JSONArray getJSONArray_allRoutes(){

        try {
            return getJSON().getJSONArray("allRoutes");
        } catch (JSONException e) {
            e.printStackTrace();
            return null ;
        }
    }

    public JSONObject findRouteFromUserId(String idUser) throws JSONException {
        Boolean found = false ;
        int j = 0;
        for (int i = 0; i < jAallRoutes.length(); i++) {
            if (jAallRoutes.getJSONObject(i).getString("idUser").equals(idUser)){
                found = true ;
                j = i ;
                break;
            }
        }
        return jAallRoutes.getJSONObject(j);
    }

    public LatLng getDepartLatLong(JSONObject jsonObject) throws JSONException {
        Double lat ;
        Double lng ;
        lat = jsonObject.getDouble("departLat");
        lng = jsonObject.getDouble("departLong");
        LatLng loc = new LatLng(lat,lng);
        return loc ;
    }

    public LatLng getDestLatLong(JSONObject jsonObject) throws JSONException {
        Double lat ;
        Double lng ;
        lat = jsonObject.getDouble("arriveLat");
        lng = jsonObject.getDouble("arriveLong");
        LatLng loc = new LatLng(lat,lng);
        return loc ;
    }

}
