package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.Clinic_add_booking;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicBookingActivity extends AppCompatActivity implements View.OnClickListener {
    String hospi_name, hospi_address, clinic_ID, user_ID, nameST, contractST, addressST, purposeST;
    TextInputEditText name, address, purpose;
    MaskEditText contract;
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
            contract.setText("+88 "+contractST.substring(0,5)+"-"+contractST.substring(5));
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
        if (name.getText().toString().isEmpty()) {
            name.setError("Enter Your Name");
            name.requestFocus();
        }
        else if (contract.getText().toString().isEmpty()) {
            contract.setError("Enter Your Mobile Number");
            contract.requestFocus();
        }
        else if (address.getText().toString().isEmpty()) {
            address.setError("Enter Your Address");
            address.requestFocus();
        }
        else if (purpose.getText().toString().isEmpty()) {
            purpose.setError("Enter Your Purpose");
            purpose.requestFocus();
        }
        else{
            sendData();
        }

//        Log.e("dfbhgfyuhd",purpose.getText().toString());
    }

    private void sendData() {
        Log.e("dfbhgfyuhd",contract.getText().toString());
        Map<String, String> map = new HashMap<>();
        map.put("clinic_id",clinic_ID);
        map.put("user_id",user_ID);
        map.put("name",name.getText().toString());
        map.put("mobile",contract.getText().toString());
        map.put("address",address.getText().toString());
        map.put("purpose",purpose.getText().toString());
        apiInterface= APiClient.getClient().create(ApiInterface.class);
        Call<Clinic_add_booking> call=apiInterface.clinicBooking(Constant.AUTH,map);
        call.enqueue(new Callback<Clinic_add_booking>() {
            @Override
            public void onResponse(Call<Clinic_add_booking> call, Response<Clinic_add_booking> response) {

                Log.e("Submitted",response.body().getMessage().toString());
                Toast.makeText(getApplicationContext(),response.body().getMessage().toString(),Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ClinicBookingActivity.this,ClinicService.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Clinic_add_booking> call, Throwable t) {
                Log.e("error",t.toString());

            }
        });
    }
}