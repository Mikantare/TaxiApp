package com.bespalov.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ChosseModeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(ChosseModeActivity.this, PassendgerActivity.class));
        }
    }

    public void goToPassangerSinIn(View view) {
        startActivity(new Intent(ChosseModeActivity.this, PassengerSignInActivity.class));
    }

    public void goToDriverSinIn(View view) {
        startActivity(new Intent(ChosseModeActivity.this, DriverSignInActivity.class));
    }
}