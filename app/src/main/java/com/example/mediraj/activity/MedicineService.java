package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mediraj.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MedicineService extends AppCompatActivity {

    private MaterialButton send_btn, call_btn;
    private TextView camera_btn;
    private TextInputLayout textInputLayout, textInputLayout1, textInputLayout2;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_service);

        //initialize button with id
        send_btn = findViewById(R.id.sendbtnmed);
        call_btn = findViewById(R.id.callbtnmed);

        //camera text initialization
        camera_btn = findViewById(R.id.camera_btn);

        //Material TextInputlayout initialization
        textInputLayout = findViewById(R.id.medbox);
        textInputLayout1 = findViewById(R.id.mc_useraddress);
        textInputLayout2 = findViewById(R.id.mc_phone);

        //initialize image view
        imageView = findViewById(R.id.imgpres);

        //call_btn
        call_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:01740155577"));
            startActivity(intent);
        });


    }


}