package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.BloodBooking_Model;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodBookingctivity extends AppCompatActivity {

    TextInputEditText blname, bladdress, blgroup;
    MaskEditText contact;
    String nameST,contractST,addressST,user_ID,group_ID;
    CardView btnBloodReq;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bookingctivity);
        init();
        btnBloodReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });


    }

    private void validation() {
        if (blname.getText().toString().isEmpty()) {
            blname.setError("Enter Your Name");
            blname.requestFocus();
        }
        else if (contact.getText().toString().isEmpty()) {
            contact.setError("Enter Your Mobile Number");
            contact.requestFocus();
        }
        else if (bladdress.getText().toString().isEmpty()) {
            bladdress.setError("Enter Your Address");
            bladdress.requestFocus();
        }
        else{
            sendData();
        }

    }

    private void sendData() {
        Map<String, String> map = new HashMap<>();
        map.put("group_id",group_ID);
        map.put("user_id",user_ID);
        map.put("name",blname.getText().toString());
        map.put("mobile",contact.getText().toString());
        map.put("address",bladdress.getText().toString());
        apiInterface= APiClient.getClient().create(ApiInterface.class);
        Call<BloodBooking_Model> call=apiInterface.blood_booking(Constant.AUTH,map);
        call.enqueue(new Callback<BloodBooking_Model>() {
            @Override
            public void onResponse(Call<BloodBooking_Model> call, Response<BloodBooking_Model> response) {
                Log.e("Request Success",response.body().getMessage().toString());
                Toast.makeText(getApplicationContext(),response.body().getMessage().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BloodBooking_Model> call, Throwable t) {
                Log.e("Error",t.toString());

            }
        });
    }

    private void init() {
        blname = findViewById(R.id.blood_usser_name);
        bladdress = findViewById(R.id.blood_usser_address);
        contact = findViewById(R.id.blood_usser_contact);
        btnBloodReq=findViewById(R.id.confirm_btn);
        setUserData();
    }

    private void setUserData() {
        try{
        group_ID= getIntent().getStringExtra("groupID");
        user_ID = DataManager.getInstance().getUserData(this).data.id;
        nameST = DataManager.getInstance().getUserData(this).data.name;
        contractST = DataManager.getInstance().getUserData(this).data.mobile;
        addressST = DataManager.getInstance().getUserData(this).data.address;
        blname.setText(nameST);
        bladdress.setText(addressST);
        contact.setText("+88 "+contractST.substring(0,5)+"-"+contractST.substring(5));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}