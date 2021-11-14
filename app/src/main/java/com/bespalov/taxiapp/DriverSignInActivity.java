package com.bespalov.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class DriverSignInActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail, textInputName,
            textInputPassword, textInputConfirmPassword;
    private Button loginSignInButton;
    private TextView toogleLoginSignUpTextView;

    private boolean isLogInModeActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_in);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        loginSignInButton = findViewById(R.id.loginSignInButton);
        toogleLoginSignUpTextView = findViewById(R.id.toogleLoginSignUpTextView);
    }

    private boolean valideteEmail () {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Please input your email");
            return false;
                    } else  {
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean valideteName () {
        String namelInput = textInputName.getEditText().getText().toString().trim();
        if (namelInput.isEmpty()) {
            textInputName.setError("Please input your name");
            return false;
        } else if (namelInput.length() > 15) {
            textInputName.setError("Name lenght have to be less then 15");
            return false;
        }
        else  {
            textInputName.setError("");
            return true;
        }
    }

    private boolean validetePassword () {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String comfirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Please input your password");
            return false;
        } else if (passwordInput.length() < 7) {
            textInputPassword.setError("Password lenght have to be more then 6");
            return false;
        } else if (!passwordInput.equals(comfirmPasswordInput)) {
            textInputPassword.setError("Password have to match");
            return false;
        }
        else  {
            textInputPassword.setError("");
            return true;
        }
    }

    public void loginSignUpUser(View view) {
    if (!valideteEmail() | !valideteName() | !validetePassword()) {
        return;
    } else {
        String userInput =  "Email: " + textInputEmail.getEditText().getText().toString().trim() +
            "\n Name: " + textInputName.getEditText().getText().toString().trim() +
        "\n Password: " + textInputPassword.getEditText().getText().toString().trim();
        Toast.makeText(this, userInput, Toast.LENGTH_LONG).show();
    }
    }


    public void toogleLoginSignUpUser(View view) {
        toggleLogInMode(view);

    }

    public void toggleLogInMode (View view){
        if (isLogInModeActive) {
            isLogInModeActive = false;
            loginSignInButton.setText("Sign Up");
            toogleLoginSignUpTextView.setText("Or, log in");
            textInputConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            isLogInModeActive = true;
            loginSignInButton.setText("Log In");
            toogleLoginSignUpTextView.setText("or, Sign Up");
            textInputConfirmPassword.setVisibility(View.INVISIBLE);
        }
    }

}