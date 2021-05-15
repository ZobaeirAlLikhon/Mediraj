package com.example.mediraj.fragment;

import android.Manifest;
import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.UserData;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.santalu.maskara.widget.MaskEditText;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = SignUpFragment.class.getName();
    private static final int REQUEST_LOCATION = 101;
    TabLayout tabLayout;
    View view;
    TextInputEditText userName, email, password, passwordCon;
    MaskEditText phone;
    MaterialButton signUpBtn;
    String mobile, device_name, device_token;
    Double userLat, userLong;
    ApiInterface apiInterface;

    LocationManager locationManager;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.signup_fragment, container, false);
        init();
        return view;
    }

    private void init() {

        //initialize all view from sign_up_fragment
        userName = view.findViewById(R.id.userName);
        email = view.findViewById(R.id.userEmail);
        phone = view.findViewById(R.id.userPhone);
        password = view.findViewById(R.id.regPass);
        passwordCon = view.findViewById(R.id.regConPass);
        signUpBtn = view.findViewById(R.id.btnSignUp);

        //initialize view from paren
        tabLayout = getActivity().findViewById(R.id.tabLayout);


        //api interface initialize
        apiInterface = APiClient.getClient().create(ApiInterface.class);


        //set on_click_listener
        signUpBtn.setOnClickListener(this);

        //get values of predefine variable
        device_name = Build.MODEL;
        try {
            device_token = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //location
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                userLat = locationGPS.getLatitude();
                userLong = locationGPS.getLongitude();
                Log.e("user lat", userLat + " ");
                Log.e("user long", userLong + " ");
                Log.e("user lat", locationGPS.getTime() + " ");
            } else {
                Toast.makeText(getActivity(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                userValidation();
                break;
        }
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
            if (ConnectionManager.connection(getContext())) {
                signUp();
            } else {
                Toast.makeText(getContext(), getString(R.string.internet_connect_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signUp() {
        DataManager.getInstance().showProgressMessage(getActivity(),"Please Wait...");
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
                        Toast.makeText(getContext(),userData.message,Toast.LENGTH_SHORT).show();
                        tabLayout.newTab().select();
                    }else {
                        Toast.makeText(getContext(),userData.message,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), " permission denied, boo! Disable the functionality that depends on this permission.", Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(getActivity(), " permission denied, boo! Disable the functionality that depends on this permission.", Toast.LENGTH_SHORT).show();
        }
    }

}