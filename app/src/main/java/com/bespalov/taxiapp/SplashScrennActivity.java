package com.bespalov.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScrennActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                  sleep(5000);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(SplashScrennActivity.this, ChosseModeActivity.class));
                    }
                }
            };
            thread.start();
        }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}



