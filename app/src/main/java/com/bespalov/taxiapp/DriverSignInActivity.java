package com.bespalov.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverSignInActivity extends AppCompatActivity {

    private static final String TAG = "SigninMetod";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersDataBaseReference;

    private TextInputLayout textInputEmail, textInputName, textInputPassword, textInputConfirmPassword;
    private Button loginSignInButton;
    private TextView toogleLoginSignUpTextView;

    private boolean isLogInModeActive;
    private boolean isPassandger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDataBaseReference = database.getReference().child("users");

        setContentView(R.layout.activity_driver_sign_in);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        loginSignInButton = findViewById(R.id.loginSignInButton);
        toogleLoginSignUpTextView = findViewById(R.id.toogleLoginSignUpTextView);
        Toast.makeText(this, "Ваш профиль водитель", Toast.LENGTH_SHORT).show();
        ChangeMode changeMode = new ChangeMode().changeModeIsPassenger(false);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(DriverSignInActivity.this, DriversMapsActivity.class));
        }
    }

    private boolean valideteEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Please input your email");
            return false;
        } else {
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean valideteName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            textInputName.setError("Please input your name");
            return false;
        } else if (nameInput.length() > 15) {
            textInputName.setError("Name lenght have to be less then 15");
            return false;
        } else {
            textInputName.setError("");
            return true;
        }
    }

    private boolean validetePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String passwordComfirmInput = textInputConfirmPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Please input your password");
            return false;
        } else if (passwordInput.length() < 7) {
            textInputPassword.setError("Password lenght have to be more then 6");
            return false;
        } else if (!passwordInput.equals(passwordComfirmInput)) {
            textInputPassword.setError("Password have to match");
            return false;
        } else {
            textInputPassword.setError("");
            return true;
        }
    }

    private boolean validetePasswordLogIn() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Please input your password");
            return false;
        } else if (passwordInput.length() < 7) {
            textInputPassword.setError("Password lenght have to be more then 6");
            return false;
        } else {
            textInputPassword.setError("");
            return true;
        }
    }


    public void toogleLoginSignUpUser(View view) {
        toggleLogInMode(view);

    }

    public void loginSignUpUser(View view) {
        String email = textInputEmail.getEditText().getText().toString().trim();
        String password = textInputPassword.getEditText().getText().toString().trim();
        String name = textInputName.getEditText().getText().toString().trim();


        if (isLogInModeActive) {
            if (!valideteEmail()  | !validetePasswordLogIn()) {
                return;
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(DriverSignInActivity.this, DriversMapsActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(DriverSignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            if (!valideteEmail() | !valideteName() | !validetePassword()) {
                return;
            } else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    createUser(user, email, name, password, isPassandger);
                                    Intent intent = new Intent(DriverSignInActivity.this, DriversMapsActivity.class);
                                    intent.putExtra("userName", name);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(DriverSignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        }
    }

    private void createUser(FirebaseUser firebaseUser, String email, String name, String password, boolean isPassendger) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(name);
        user.setPassenger(isPassendger);
        usersDataBaseReference.push().setValue(user);
    }

    public void toggleLogInMode(View view) {
        if (isLogInModeActive) {
            isLogInModeActive = false;
            loginSignInButton.setText("Sign Up");
            toogleLoginSignUpTextView.setText("Or, log in");
            textInputConfirmPassword.setVisibility(View.VISIBLE);
            textInputName.setVisibility(View.VISIBLE);
        } else {
            isLogInModeActive = true;
            loginSignInButton.setText("Log In");
            toogleLoginSignUpTextView.setText("or, Sign Up");
            textInputConfirmPassword.setVisibility(View.INVISIBLE);
            textInputName.setVisibility(View.GONE);
        }
    }
}