package com.bespalov.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChosseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
    }

    public void goToPassangerSinIn(View view) {
        startActivity(new Intent(ChosseModeActivity.this, PesengerSignInActivity.class));
    }

    public void goToDriverSinIn(View view) {
        startActivity(new Intent(ChosseModeActivity.this, DriverSignInActivity.class));
    }
}