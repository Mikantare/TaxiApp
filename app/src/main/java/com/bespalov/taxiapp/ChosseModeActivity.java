package com.bespalov.taxiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ChosseModeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ChildEventListener usersChildEventListener;

    private DatabaseReference usersDataBaseReference;

    private Boolean isPassenger;

    private Button driverButtont, passagerButtont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
        driverButtont = findViewById(R.id.driverButtont);
        passagerButtont = findViewById(R.id.passagerButtont);


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            attachUserDataBaseReferenceListener();
            driverButtont.setVisibility(View.GONE);
            passagerButtont.setVisibility(View.GONE);
        }

    }

    public void goToPassangerSinIn(View view) {
        startActivity(new Intent(ChosseModeActivity.this, PassengerSignInActivity.class));
    }

    public void goToDriverSinIn(View view) {
        startActivity(new Intent(ChosseModeActivity.this, DriverSignInActivity.class));
    }

    private void
    attachUserDataBaseReferenceListener() {
        usersDataBaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        if (usersChildEventListener == null) {
            usersChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    User user = snapshot.getValue(User.class);
                    if (!user.getId().equals(mAuth.getCurrentUser().getUid())) {
                    } else {
                         isPassenger = snapshot.child("passenger").getValue(Boolean.TYPE);
                        if (mAuth.getCurrentUser() != null) {
                            if (isPassenger) {
                                startActivity(new Intent(ChosseModeActivity.this, PassengerMapsActivity.class));}
                            else {
                                startActivity(new Intent(ChosseModeActivity.this, DriversMapsActivity.class));
                            }
                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            usersDataBaseReference.addChildEventListener(usersChildEventListener);
        }
    }
}