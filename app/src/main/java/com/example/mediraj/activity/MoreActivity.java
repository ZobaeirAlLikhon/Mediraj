package com.example.mediraj.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.helper.SessionManager;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MoreActivity.class.getName();
    private BottomNavigationView bottomNavigationView;
    private TextView userName, userPhone, userEmail;
    private CircleImageView userImg;
    private LinearLayout profileLay,offerLay,promoLay,logoutLay,devTeam,emergencyLay,aboutLay;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        //DataManager.getInstance().showProgressMessage(this,"please wait...");
        initView();
        setUserData();
    }


    private void initView() {

        //bottom navigation view and related listener
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.more);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.more:
                        return true;
                }

                return false;
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

            if (DataManager.getInstance().getUserData(this).data.avatar !=null){
                Glide.with(this)
                        .load(Constant.USER_AVATAR_URL+DataManager.getInstance().getUserData(this).data.avatar)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_profile))
                        .centerCrop()
                        .into(userImg);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileLay:
                startActivity(new Intent(this,ProfileActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.offerLay:
                //go to offer page
                break;
            case R.id.promoLay:
                //go to promo page
                break;
            case R.id.aboutLay:
                //go to about us page
                break;
            case R.id.emergencyLay:
                //go to caller
                break;
            case R.id.devTeam:
                //go to dev team
                break;
            case R.id.logoutLay:
                alertLogout();
                break;
        }
    }


    public void alertLogout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MoreActivity.this);
        alertDialog.setTitle("Are you sure you want to logout ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SessionManager.logout(MoreActivity.this, apiInterface);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }
}