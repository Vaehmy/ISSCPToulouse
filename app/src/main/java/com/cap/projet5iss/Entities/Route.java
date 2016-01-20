package com.cap.projet5iss.Entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Nana on 20/01/2016.
 */
public class Route {

    String departHour, departPlace, arriveHour, arrivePlace ;
    long departLat, departLong,  arriveLat, arriveLong ;
    int availableSeats ;
    LatLng departLatLng, arriveLatLng ;
    ArrayList<String> days ;

    public Route() {
    }

    public LatLng getDepartLatLng() {
        return departLatLng;
    }

    public void setDepartLatLng(LatLng departLatLng) {
        this.departLatLng = departLatLng;
    }

    public LatLng getArriveLatLng() {
        return arriveLatLng;
    }

    public void setArriveLatLng(LatLng arriveLatLng) {
        this.arriveLatLng = arriveLatLng;
    }

    public String getDepartHour() {
        return departHour;
    }

    public void setDepartHour(String departHour) {
        this.departHour = departHour;
    }

    public String getDepartPlace() {
        return departPlace;
    }

    public void setDepartPlace(String departPlace) {
        this.departPlace = departPlace;
    }

    public String getArriveHour() {
        return arriveHour;
    }

    public void setArriveHour(String arriveHour) {
        this.arriveHour = arriveHour;
    }

    public String getArrivePlace() {
        return arrivePlace;
    }

    public void setArrivePlace(String arrivePlace) {
        this.arrivePlace = arrivePlace;
    }

    public long getDepartLat() {
        return departLat;
    }

    public void setDepartLat(long departLat) {
        this.departLat = departLat;
    }

    public long getDepartLong() {
        return departLong;
    }

    public void setDepartLong(long departLong) {
        this.departLong = departLong;
    }

    public long getArriveLat() {
        return arriveLat;
    }

    public void setArriveLat(long arriveLat) {
        this.arriveLat = arriveLat;
    }

    public long getArriveLong() {
        return arriveLong;
    }

    public void setArriveLong(long arriveLong) {
        this.arriveLong = arriveLong;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }
}
