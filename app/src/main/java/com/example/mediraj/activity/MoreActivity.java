package com.example.mediraj.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.BuildConfig;
import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.helper.SessionManager;
import com.example.mediraj.model.UserData;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MoreActivity.class.getName();
    private BottomNavigationView bottomNavigationView;
    private TextView userName, userPhone, userEmail,appVersion,emergency;
    private CircleImageView userImg;
    private LinearLayout profileLay,offerLay,promoLay,logoutLay,devTeam,emergencyLay,aboutLay;
    private ApiInterface apiInterface;
    private SwitchCompat toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        //DataManager.getInstance().showProgressMessage(this,"please wait...");
        initView();
        setUserData();
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notificationOffOn(DataManager.getInstance().getUserData(MoreActivity.this).data.id);
            }
        });
    }


    private void initView() {

        //bottom navigation view and related listener
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.more);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else return itemId == R.id.more;
            }
        });

        //other views
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        userImg = findViewById(R.id.userImg);
        profileLay = findViewById(R.id.profileLay);
        offerLay = findViewById(R.id.offerLay);
        promoLay = findViewById(R.id.promoLay);
        aboutLay = findViewById(R.id.aboutLay);
        emergencyLay = findViewById(R.id.emergencyLay);
        devTeam = findViewById(R.id.devTeam);
        logoutLay = findViewById(R.id.logoutLay);
        toggle = findViewById(R.id.toggle);
        appVersion = findViewById(R.id.appVersion);
        emergency = findViewById(R.id.emer_call);

        appVersion.setText("Version "+ BuildConfig.VERSION_NAME);
        //lister part
        profileLay.setOnClickListener(this);
        offerLay.setOnClickListener(this);
        promoLay.setOnClickListener(this);
        logoutLay.setOnClickListener(this);
        devTeam.setOnClickListener(this);
        emergencyLay.setOnClickListener(this);
        aboutLay.setOnClickListener(this);



        //api interface initialization
        apiInterface = APiClient.getClient().create(ApiInterface.class);
    }

    private void setUserData() {
        if (DataManager.getInstance().getUserData(getApplicationContext()) != null
                &&
                DataManager.getInstance().getUserData(getApplicationContext()).data != null
                &&
                DataManager.getInstance().getUserData(getApplicationContext()).data.id != null){

            if (DataManager.getInstance().getUserData(this).data.name !=null){
                userName.setText(DataManager.getInstance().getUserData(this).data.name);
            }

            if (DataManager.getInstance().getUserData(this).data.mobile !=null){
                userPhone.setText(DataManager.getInstance().getUserData(this).data.mobile);
            }

            if (DataManager.getInstance().getUserData(this).data.email !=null){
                userEmail.setText(DataManager.getInstance().getUserData(this).data.email);
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.avatar !=null){
                Glide.with(this)
                        .load(Constant.USER_AVATAR_URL+DataManager.getInstance().getUserData(this).data.avatar)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_profile))
                        .centerCrop()
                        .into(userImg);
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.notification.equalsIgnoreCase("on")){
                toggle.setChecked(true);
            }else {
                toggle.setChecked(false);
            }

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.profileLay) {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.offerLay) {//go to offer page
        } else if (id == R.id.promoLay) {//go to promo page
        } else if (id == R.id.aboutLay) {//go to about us page
        } else if (id == R.id.emergencyLay) {
            Intent intent = new Intent(MoreActivity.this,EmergencyNumberActivity.class);
            startActivity(intent);
        } else if (id == R.id.devTeam) {//go to dev team
        } else if (id == R.id.logoutLay) {
            alertLogout();
        }
    }


    public void alertLogout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MoreActivity.this);
        alertDialog.setTitle(R.string.alert_logout);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SessionManager.logout(MoreActivity.this, apiInterface);
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }

    private void notificationOffOn(String id) {
        DataManager.getInstance().showProgressMessage(MoreActivity.this, getString(R.string.please_wait));
        Call<UserData> notiStatusCall = apiInterface.notificationStatus(Constant.AUTH, id);
        notiStatusCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    UserData data = response.body();
                    assert data != null;
                    if (data.response == 200) {
                        Toast.makeText(getApplicationContext(), data.message, Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(MoreActivity.this, data.message, Toast.LENGTH_SHORT).show();
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


    @Override
    public void onBackPressed() {
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != selectedItemId) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
        else {
            super.onBackPressed();
        }
    }

}