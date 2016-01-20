package com.cap.projet5iss.Entities;

/**
 * Created by Nana on 20/01/2016.
 */

public class User {

   String name, addStreet, addPostal, addCity, sexe, telephone ;
   Vehicule vehicule ;
   Boolean isDriver ;
   Route route ;

    public User() {
        this.name = "";
        this.addStreet = "";
        this.addPostal = "";
        this.addCity = "";
        this.sexe = "";
        this.telephone = "";
        this.vehicule = null;
        this.isDriver = true;
        this.route = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddStreet() {
        return addStreet;
    }

    public void setAddStreet(String addStreet) {
        this.addStreet = addStreet;
    }

    public String getAddPostal() {
        return addPostal;
    }

    public void setAddPostal(String addPostal) {
        this.addPostal = addPostal;
    }

    public String getAddCity() {
        return addCity;
    }

    public void setAddCity(String addCity) {
        this.addCity = addCity;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Boolean getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(Boolean isDriver) {
        this.isDriver = isDriver;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
