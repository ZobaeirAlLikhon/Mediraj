package com.example.mediraj.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mediraj.R;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;

public class ClinicBookingActivity extends AppCompatActivity implements View.OnClickListener {
    String hospi_name, hospi_address, clinic_ID, user_ID, nameST, contractST, addressST, purposeST;
    TextInputEditText name, contract, address, purpose;
    CardView clinic_confirm_btn;
    TextView ctitle, caddress;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_booking);

        if (getIntent() != null) {
            try {
                hospi_name = getIntent().getStringExtra("hospital_name");
                hospi_address = getIntent().getStringExtra("hospital_address");
                clinic_ID = getIntent().getStringExtra("clinic_ID");
                Log.e("clinicID----", clinic_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        init();
    }


    private void init() {
        name = findViewById(R.id.patientName);
        contract = findViewById(R.id.patientContact);
        address = findViewById(R.id.patientAddress);
        ctitle = findViewById(R.id.ctitle);
        caddress = findViewById(R.id.caddress);
        purpose = findViewById(R.id.purpose);
        clinic_confirm_btn = findViewById(R.id.clinic_confirm_btn);
        ctitle.setText(hospi_name);
        caddress.setText(hospi_address);
        user_ID = DataManager.getInstance().getUserData(this).data.id;
        setUserData();

        clinic_confirm_btn.setOnClickListener(this);
    }

    private void setUserData() {
        try {
            nameST = DataManager.getInstance().getUserData(this).data.name;
            contractST = DataManager.getInstance().getUserData(this).data.mobile;
            addressST = DataManager.getInstance().getUserData(this).data.address;
            name.setText(nameST);
            contract.setText(contractST);
            address.setText(addressST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clinic_confirm_btn:
                validateData();
                break;
        }
    }

    private void validateData() {
    }
}