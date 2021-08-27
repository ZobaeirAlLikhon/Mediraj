package com.example.mediraj.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
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
    TextView ctitle, caddress,toolbarText;
    ImageView toolbarBtn;
    ApiInterface apiInterface;
    String mobile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_booking);

        if (getIntent() != null) {
            try {
                hospi_name = getIntent().getStringExtra("hospital_name");
                hospi_address = getIntent().getStringExtra("hospital_address");
                clinic_ID = getIntent().getStringExtra("clinic_ID");
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
        toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText(R.string.clinic_bookin);
        toolbarBtn = findViewById(R.id.toolbarBtn);
        ctitle.setText(hospi_name);
        caddress.setText(hospi_address);
        user_ID = DataManager.getInstance().getUserData(this).data.id;
        setUserData();

        clinic_confirm_btn.setOnClickListener(this);
        toolbarBtn.setOnClickListener(this);
    }

    private void setUserData() {
        try {
            nameST = DataManager.getInstance().getUserData(this).data.name;
            name.setText(nameST);

            contractST = DataManager.getInstance().getUserData(this).data.mobile;
            contract.setText("+88 " + contractST.substring(0, 5) + "-" + contractST.substring(5));

            if (DataManager.getInstance().getUserData(this).data.address != null) {
                addressST = DataManager.getInstance().getUserData(this).data.address;
                address.setText(addressST);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clinic_confirm_btn) {
            if (ConnectionManager.connection(this)) {
                validateData();
            } else {
                Toast.makeText(this, R.string.internet_connect_msg, Toast.LENGTH_SHORT).show();
            }

        }else if (v.getId()==R.id.toolbarBtn){
            finish();
        }
    }

    private void validateData() {
        try {
            if (!contract.getText().toString().equals(" ") && contract.getText().length() == 16) {
                String raw_phone = contract.getText().toString().split(" ")[1];
                mobile = raw_phone.split("-")[0] + raw_phone.split("-")[1];
            } else {
                contract.setError(getString(R.string.userPhone_error_valid));
                contract.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (name.getText().toString().isEmpty()) {
            name.setError("Enter Your Name");
            name.requestFocus();
        } else if (contract.getText().toString().isEmpty()) {
            contract.setError("Enter Your Mobile Number");
            contract.requestFocus();
        } else if (mobile == null) {
            contract.setError(getString(R.string.userPhone_error_valid));
            contract.requestFocus();
        } else if (mobile.length() != 11) {
            contract.setError(getString(R.string.userPhone_error_valid));
            contract.requestFocus();
        } else if (!mobile.startsWith("0")) {
            contract.setError(getString(R.string.userPhone_error_number));
            contract.requestFocus();
        } else if (address.getText().toString().isEmpty()) {
            address.setError("Enter Your Address");
            address.requestFocus();
        } else if (purpose.getText().toString().isEmpty()) {
            purpose.setError("Enter Your Purpose");
            purpose.requestFocus();
        } else {
            sendData();
        }
    }

    private void sendData() {
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Map<String, String> map = new HashMap<>();
        map.put("clinic_id", clinic_ID);
        map.put("user_id", user_ID);
        map.put("name", name.getText().toString());
        map.put("mobile", contract.getText().toString());
        map.put("address", address.getText().toString());
        map.put("purpose", purpose.getText().toString());
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        Call<Clinic_add_booking> call = apiInterface.clinicBooking(Constant.AUTH, map);
        call.enqueue(new Callback<Clinic_add_booking>() {
            @Override
            public void onResponse(@NonNull Call<Clinic_add_booking> call, @NonNull Response<Clinic_add_booking> response) {
                DataManager.getInstance().hideProgressMessage();

                try {
                    Clinic_add_booking clinic_add_booking = response.body();
                    assert clinic_add_booking != null;
                    if (clinic_add_booking.getResponse() == 200) {
                        Toast.makeText(getApplicationContext(), clinic_add_booking.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        confirmAlert(ClinicBookingActivity.this);
                    } else {
                        Toast.makeText(getApplicationContext(), clinic_add_booking.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Clinic_add_booking> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();

            }
        });
    }


    public void confirmAlert(Activity activity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Widget_Material_ListPopupWindow;
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.order_confirm_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        AppCompatButton homeBtn = dialog.findViewById(R.id.backtoHome);
        TextView orderNo = dialog.findViewById(R.id.orderNo);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
                activity.startActivity(new Intent(activity.getApplicationContext(), HomeActivity.class));
                activity.finish();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}