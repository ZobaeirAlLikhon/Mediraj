package com.example.mediraj.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediraj.R;

public class EmergencyNumberActivity extends AppCompatActivity {

    private TextView fire;
    private Button btnfire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_number);

        //all Text View
        fire = findViewById(R.id.fire_contact);



        //All Button

        btnfire = findViewById(R.id.btn_fire);

        //call_fire_btn
        btnfire.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:01730-336655"));
            startActivity(intent);
        });

    }
}