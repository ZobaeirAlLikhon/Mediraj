package com.example.mediraj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediraj.R;

public class EmergencyDoctorActivity extends AppCompatActivity {

    TextView toolbarText;
    ImageView toolbarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_doctor);

        toolbarText = findViewById(R.id.toolbarText);
        toolbarBtn = findViewById(R.id.toolbarBtn);

        toolbarText.setText(getString(R.string.online_doctor));

        toolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}