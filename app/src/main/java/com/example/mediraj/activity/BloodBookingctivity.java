package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mediraj.R;
import com.google.android.material.textfield.TextInputEditText;
import com.santalu.maskara.widget.MaskEditText;

public class BloodBookingctivity extends AppCompatActivity {

    TextInputEditText blname, bladdress, blgroup;
    MaskEditText contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bookingctivity);



        blname = findViewById(R.id.blood_usser_name);
        bladdress = findViewById(R.id.blood_usser_address);
        blgroup = findViewById(R.id.blood_group_name);
        contact = findViewById(R.id.blood_usser_contact);
    }
}