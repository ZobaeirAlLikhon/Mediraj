package com.example.mediraj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.UserData;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.santalu.maskara.widget.MaskEditText;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SignUpActivity.class.getName();
    private static final int REQUEST_LOCATION = 101;
    TextInputEditText userName, email, password, passwordCon;
    MaskEditText phone;
    MaterialButton signUpBtn,loginBtn;
    String mobile, device_name, device_token;
    Double userLat, userLong;
    ApiInterface apiInterface;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        //initialize all view from sign_up_fragment
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        phone = findViewById(R.id.userPhone);
        password = findViewById(R.id.regPass);
        passwordCon = findViewById(R.id.regConPass);
        signUpBtn = findViewById(R.id.btnSignUp);
        loginBtn = findViewById(R.id.loginBtn);

        //api interface initialize
        apiInterface = APiClient.getClient().create(ApiInterface.class);


        //set on_click_listener
        signUpBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        //get values of predefine variable
        device_name = Build.MODEL;
        try {
            device_token = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                userValidation();
                break;
            case R.id.loginBtn:
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
               this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                userLat = locationGPS.getLatitude();
                userLong = locationGPS.getLongitude();
                Log.e("user lat", userLat + " ");
                Log.e("user long", userLong + " ");
                Log.e("user lat", locationGPS.getTime() + " ");
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void userValidation() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }

        //extract phone number from field
        if (!phone.getMasked().equals("")) {
            String raw_phone = phone.getMasked().split(" ")[1];
            mobile = raw_phone.split("-")[0] + raw_phone.split("-")[1];

        }


        //form data validation
        if (userName.getText().toString().isEmpty()) {
            userName.setError(getString(R.string.userName_error));
            userName.requestFocus();
        } else if (email.getText().toString().isEmpty()) {
            email.setError(getString(R.string.userEmail_error));
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getString(R.string.userEmail_error_valid));
            email.requestFocus();
        } else if (phone.getMasked().isEmpty()) {
            phone.setError(getString(R.string.userPhone_error));
            phone.requestFocus();
        } else if (mobile.length() != 11) {
            phone.setError(getString(R.string.userPhone_error_valid));
            phone.requestFocus();
        } else if (!mobile.startsWith("0")) {
            phone.setError(getString(R.string.userPhone_error_number));
            phone.requestFocus();
        } else if (password.getText().toString().isEmpty()) {
            password.setError(getString(R.string.userPassword_error));
            password.requestFocus();
        } else if (!passwordCon.getText().toString().equals(password.getText().toString().trim())) {
            passwordCon.setError(getString(R.string.userPassword_con_error));
            passwordCon.requestFocus();
        } else {
            if (ConnectionManager.connection(this)) {
                signUp();
            } else {
                Toast.makeText(this, getString(R.string.internet_connect_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signUp() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        Map<String, String> map = new HashMap<>();
        map.put("name", userName.getText().toString().trim());
        map.put("mobile", phone.getMasked());
        map.put("email", email.getText().toString());
        map.put("password", password.getText().toString());
        map.put("device_name", device_name);
        map.put("device_token", device_token);
        map.put("latitude", String.valueOf(userLat));
        map.put("longitude", String.valueOf(userLong));

        Log.e(TAG, map.toString());

        Call<UserData> signUpCall = apiInterface.userSignUp(Constant.AUTH,map);
        signUpCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    UserData userData = response.body();
                    if (userData.response==200){
                        Toast.makeText(SignUpActivity.this,userData.message,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(SignUpActivity.this,userData.message,Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        boolean location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (requestCode == REQUEST_LOCATION) {
            if (location) {
                getLocation();
            } else {
                Toast.makeText(this, " permission denied, boo! Disable the functionality that depends on this permission.", Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(this, " permission denied, boo! Disable the functionality that depends on this permission.", Toast.LENGTH_SHORT).show();
        }
    }
}