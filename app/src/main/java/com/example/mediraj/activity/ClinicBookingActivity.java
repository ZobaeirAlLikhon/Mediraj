package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mediraj.R;
import com.example.mediraj.helper.DataManager;
import com.google.android.material.textfield.TextInputEditText;

public class ClinicBookingActivity extends AppCompatActivity {
    TextInputEditText name,contract,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_booking);
        init();
    }

    private void init() {
        name=findViewById(R.id.patientname1);
        contract=findViewById(R.id.patientcontact1);
        address=findViewById(R.id.patientaddress1);
        name.setText(DataManager.getInstance().getUserData(this).data.name);
        contract.setText(DataManager.getInstance().getUserData(this).data.mobile);
        address.setText(DataManager.getInstance().getUserData(this).data.address);
    }
}