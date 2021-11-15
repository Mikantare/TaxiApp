package com.bespalov.taxiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PassendgerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int RC_IMAGE_PICKER = 321;

    private FirebaseStorage storage;
    private StorageReference imageStorageRef;
    private ChildEventListener usersChildEventListener;

    private DatabaseReference usersDataBaseReference;

    private String key;
    private String newName;

    private DialogFragment dialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passendger);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        imageStorageRef = storage.getReference().child("user_photo");
        usersDataBaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        attachUserDataBaseReferenceListener();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mian_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sing_out:
                mAuth.signOut();
                startActivity(new Intent(PassendgerActivity.this, ChosseModeActivity.class));
                return true;
            case R.id.add_user_photo:
                Intent intentToImage = new Intent(Intent.ACTION_GET_CONTENT);
                intentToImage.setType("image/jpeg");
                intentToImage.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intentToImage, "choose an image"),
                        RC_IMAGE_PICKER);
                return true;
            case R.id.edit_user_name:
                usersDataBaseReference.child(key).child("name").setValue(newName);

                dialogFragment = new DialogNewName();
                dialogFragment.show(getSupportFragmentManager(),"dialogFragment");


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void attachUserDataBaseReferenceListener() {
        usersDataBaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        if (usersChildEventListener == null) {
            usersChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    User user = snapshot.getValue(User.class);
                    if (!user.getId().equals(mAuth.getCurrentUser().getUid())) {
                    } else {
                        key = snapshot.getKey();
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

                @Override
                protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);
                    if (requestCode == RC_IMAGE_PICKER && resultCode == RESULT_OK) {
                        Uri selectUserImageUri = data.getData();

                        final StorageReference imageRef = imageStorageRef.child(selectUserImageUri.getLastPathSegment());
                        UploadTask uploadTask = imageRef.putFile(selectUserImageUri);

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return imageRef.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    usersDataBaseReference.child(key).child("userPhotoUri").setValue(downloadUri.toString());

                                } else {
                                    // Handle failures
                                    // ...
                                }
                            }
                        });

                    }
                }


}