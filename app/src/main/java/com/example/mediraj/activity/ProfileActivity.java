package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView userImg;
    private TextView userName,userPhone,userEmail,userAddress,userGender,dateOfBirth,toolbarTxt;
    private ImageView backBtn;
    private AppCompatButton editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        setUserData();
    }

    private void initView() {
        userImg = findViewById(R.id.userImg);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        userAddress = findViewById(R.id.userAddress);
        userGender = findViewById(R.id.userGender);
        dateOfBirth = findViewById(R.id.userDoB);
        backBtn = findViewById(R.id.toolbarBtn);
        editBtn = findViewById(R.id.editBtn);
        toolbarTxt = findViewById(R.id.toolbarText);
        toolbarTxt.setText("Profile");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
            }
        });

    }

   public void setUserData(){
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

           if (DataManager.getInstance().getUserData(this).data.address !=null){
               userAddress.setText(DataManager.getInstance().getUserData(this).data.address);
           }else {
               userAddress.setText(getString(R.string.address_not_available));
           }

           if (DataManager.getInstance().getUserData(this).data.gender !=null){
               userGender.setText(DataManager.getInstance().getUserData(this).data.gender);
           }else {
               userGender.setText(getString(R.string.address_not_gender));
           }

           if (DataManager.getInstance().getUserData(this).data.birthDate !=null){
               dateOfBirth.setText(DataManager.getInstance().getUserData(this).data.birthDate);
           }else {
               dateOfBirth.setText(getString(R.string.address_not_dob));
           }
       }
    }
}