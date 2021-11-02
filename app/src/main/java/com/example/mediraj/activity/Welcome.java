package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.helper.LocaleHelper;

public class Welcome extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 2000;
    ImageView appText;
    String lan_pref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);


        //for language load
        lan_pref = LocaleHelper.readString(Welcome.this, Constant.LANG_INFO);
        if (lan_pref == null) {
            LocaleHelper.setLocale(Welcome.this, "en");
        } else {
            if (lan_pref.equals("bn")) {
                LocaleHelper.setLocale(Welcome.this, "bn");
            } else {
                LocaleHelper.setLocale(Welcome.this, "en");
            }
        }


        initView();
        new Handler().postDelayed(() -> {
            if (DataManager.getInstance().getUserData(getApplicationContext()) != null
                    &&
                    DataManager.getInstance().getUserData(getApplicationContext()).data != null
                    &&
                    DataManager.getInstance().getUserData(getApplicationContext()).data.id != null) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
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