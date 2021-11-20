package com.bespalov.taxiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeMode {

    private FirebaseAuth mAuth;
    private ChildEventListener usersChildEventListener;
    private DatabaseReference usersDataBaseReference;
    private String key;


    private void accessAFireBase() {
        mAuth = FirebaseAuth.getInstance();
        usersDataBaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        attachUserDataBaseReferenceListener();
    }

    public ChangeMode changeModeIsPassenger (Boolean isPassenger) {
        accessAFireBase();
        return null;
    }

    private void attachUserDataBaseReferenceListener() {
        if (usersChildEventListener == null) {
            usersChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    User user = snapshot.getValue(User.class);
                    if (!user.getId().equals(mAuth.getCurrentUser().getUid())) {
                    } else {
                        usersDataBaseReference.child(snapshot.getKey()).child("passenger").setValue(key);
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
