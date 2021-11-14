package com.bespalov.taxiapp;

public class User {


    private String name;
    private String email;
    private String id;
    private String userPhotoUri;

    public User() {
    }

    public User(String name, String email, String id, String userPhotoUri) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.userPhotoUri = userPhotoUri;
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

