package com.example.mediraj.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.UserData;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SignUpActivity.class.getName();
    private static final int MY_PERMISSION_CONSTANT = 105;
    TextInputEditText userName, email, password, passwordCon;
    MaskEditText phone;
    MaterialButton signUpBtn, loginBtn;
    String mobile=null, device_name, device_token;
    Double userLat=null, userLong=null;
    ApiInterface apiInterface;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SignUpActivity.this);
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

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    try {
                        device_token = task.getResult();
                        Log.e("device token", device_token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //check location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissionForLocation();
        } else {
            getLocation();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnSignUp) {
            userValidation();
        } else if (id == R.id.loginBtn) {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        }
    }


    private void userValidation() {
        //extract phone number from field
        try {
            if (!phone.getText().toString().equals("") && phone.getText().length()==16) {
                String raw_phone = phone.getMasked().split(" ")[1];
                mobile = raw_phone.split("-")[0] + raw_phone.split("-")[1];
            }else {
                phone.setError(getString(R.string.userPhone_error_valid));
                phone.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        } else if (phone.getText().toString().isEmpty()) {
            phone.setError(getString(R.string.userPhone_error));
            phone.requestFocus();
        }else if (mobile==null){
            phone.setError(getString(R.string.userPhone_error_valid));
            phone.requestFocus();
        }
        else if (mobile.length() != 11) {
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
            if (ConnectionManager.connection(SignUpActivity.this)) {
                signUp();
            } else {
                Toast.makeText(SignUpActivity.this, getString(R.string.internet_connect_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void signUp() {
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Map<String, String> map = new HashMap<>();
        map.put("name", userName.getText().toString().trim());
        map.put("mobile", phone.getText().toString());
        map.put("email", email.getText().toString());
        map.put("password", password.getText().toString());
        map.put("device_name", device_name);
        map.put("device_token", device_token);
        map.put("latitude", String.valueOf(userLat));
        map.put("longitude", String.valueOf(userLong));

        Log.e(TAG, map.toString());

        Call<UserData> signUpCall = apiInterface.userSignUp(Constant.AUTH, map);
        signUpCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    UserData userData = response.body();
                    if (userData.response == 200) {
                        Toast.makeText(SignUpActivity.this, userData.message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, userData.message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }


    //location related functions
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissionForLocation();
        } else {
            Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        Log.d(TAG, "onSuccess: Location : " + location.toString());
                        Log.d(TAG, "onSuccess: Latitude: " + location.getLatitude());
                        Log.d(TAG, "onSuccess: Longitude: " + location.getLongitude());
                        Log.d(TAG, "onSuccess: Time: " + location.getTime());
                        userLat = location.getLatitude();
                        userLong = location.getLongitude();
                    }
                }
            });

            locationTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    //CHECKING FOR GPS STATUS
    public void checkPermissionForLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                ||

                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)

                    ||

                    ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Toast.makeText(SignUpActivity.this, "You should give the permission!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_CONSTANT);

            } else {
                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_CONSTANT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_CONSTANT) {
            if (grantResults.length > 0) {
                boolean fine_location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean coarse_location = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (fine_location && coarse_location) {
                    getLocation();
                } else {
                    Toast.makeText(SignUpActivity.this, " permission needed to work.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignUpActivity.this, " permission needed to work.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}