package com.bespalov.taxiapp;

import com.firebase.geofire.GeoFire;

public class User {


    private String name;
    private String email;
    private String id;
    private String userPhotoUri;
    private boolean isPassenger;

    public User() {
    }

    public User(String name, String email, String id, String userPhotoUri, boolean isPassenger, GeoFire geoFire) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.userPhotoUri = userPhotoUri;
        this.isPassenger = isPassenger;
    }

    public boolean isPassenger() {
        return isPassenger;
    }

    public void setPassenger(boolean passenger) {
        isPassenger = passenger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPhotoUri() {
        return userPhotoUri;
    }

    public void setUserPhotoUri(String userPhotoUri) {
        this.userPhotoUri = userPhotoUri;
    }
}

