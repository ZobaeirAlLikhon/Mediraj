package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediraj.R;
import com.example.mediraj.helper.DataManager;

public class Welcome extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;
    private TextView appText;
    private Animation fade_in, blink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        initView();


        //check for login in shared preference or RoomDB
        //if logged in then set flag value to true or change to false
        //above two for steps for story board if story board is implemented


        new Handler().postDelayed(() -> {
            if (DataManager.getInstance().getUserData(getApplicationContext()) != null
                    &&
                    DataManager.getInstance().getUserData(getApplicationContext()).data != null
                    &&
                    DataManager.getInstance().getUserData(getApplicationContext()).data.id != null) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

    private void initView() {
        appText = findViewById(R.id.animText);
        appText.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
    }
}