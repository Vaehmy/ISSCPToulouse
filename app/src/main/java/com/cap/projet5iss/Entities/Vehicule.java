package com.cap.projet5iss.Entities;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Timestamp;

/**
 * Created by Nana on 20/01/2016.
 */
public class Vehicule {

    int idVehicle ;
    long currentLat ;
    long currentLong ;
    Timestamp postTime ;
    LatLng currentLatLong ;

    public Vehicule() {
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public long getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(long currentLat) {
        this.currentLat = currentLat;
    }

    public long getCurrentLong() {
        return currentLong;
    }

    public void setCurrentLong(long currentLong) {
        this.currentLong = currentLong;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public LatLng getCurrentLatLong() {
        return new LatLng(this.currentLat, this.currentLong);
    }

}
