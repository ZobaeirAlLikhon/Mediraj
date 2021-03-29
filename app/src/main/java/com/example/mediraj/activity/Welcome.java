package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.mediraj.R;

public class Welcome extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 2000;
    private  boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);



        //check for login in shared preference or RoomDB
        //if logged in then set flag value to true or change to false
        //above two for steps for story board if story board is implemented




        new Handler().postDelayed(() -> {
            if (flag){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }else {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}