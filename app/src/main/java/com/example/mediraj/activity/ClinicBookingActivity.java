package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.Clinic_add_booking;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;

public class ClinicBookingActivity extends AppCompatActivity {
    String hospi_name,hospi_address,clinic_ID,user_ID,nameST,contractST,addressST,purposeST;
    TextInputEditText name,contract,address;
    EditText purpose;
    CardView clinic_confirm_btn;
    TextView ctitle,caddress;
    Intent intent;
    Bundle extras;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_booking);
        intent = getIntent();
        extras = intent.getExtras();

        init();

        addBooking();
    }

    private void addBooking() {
        apiInterface = APiClient.getClient().create(ApiInterface.class);



    }

    private void init() {
        setUserData();
        name=findViewById(R.id.patientname1);
        contract=findViewById(R.id.patientcontact1);
        address=findViewById(R.id.patientaddress1);
        ctitle=findViewById(R.id.ctitle);
        caddress=findViewById(R.id.caddress);
        purpose=findViewById(R.id.cpurpose1);
        clinic_confirm_btn=findViewById(R.id.clinic_confirm_btn);

        name.setText(nameST);
        contract.setText(contractST);
        address.setText(addressST);

        ctitle.setText(hospi_name);
        caddress.setText(hospi_address);
        purposeST=purpose.getText().toString();


        user_ID=DataManager.getInstance().getUserData(this).data.id;
        clinic_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("purpose----",purposeST);
                //        Call<Clinic_add_booking> add_booking=apiInterface.clinicBooking(Constant.AUTH,clinic_ID,user_ID,
//                nameST,contractST,addressST,)
            }
        });


    }

    private void setUserData() {
        hospi_name = extras.getString("hospital_name");
        hospi_address = extras.getString("hospital_address");
        clinic_ID=extras.getString("clinic_ID");
        Log.e("clinicID----",clinic_ID);
        nameST=DataManager.getInstance().getUserData(this).data.name;
        contractST=DataManager.getInstance().getUserData(this).data.mobile;
        addressST=DataManager.getInstance().getUserData(this).data.address;
    }
}