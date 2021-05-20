package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassActivity extends AppCompatActivity implements View.OnClickListener {

    //common

    ImageView backBtn;
    ApiInterface apiInterface;
    String token,mobile,id,otpOne;

    //firstLay
    RelativeLayout firstLay;
    MaskEditText userPhone;
    MaterialButton recoverBtn;

    //second
    RelativeLayout secondLay;
    TextView txtReset;
    EditText e1,e2,e3,e4;
    TextInputEditText newPass,newPassCon;
    MaterialButton submitBtn,resendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initView();
    }

    private void initView() {


        backBtn = findViewById(R.id.toolbarBtn);

        //first
        firstLay = findViewById(R.id.firstLay);
        userPhone = findViewById(R.id.userPhone);
        recoverBtn = findViewById(R.id.recoverBtn);
        token = FirebaseInstanceId.getInstance().getToken();
        //second
        secondLay = findViewById(R.id.secondLay);
        txtReset = findViewById(R.id.txtReset);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4 = findViewById(R.id.e4);
        newPass = findViewById(R.id.newPass);
        newPassCon = findViewById(R.id.newPassCon);
        submitBtn = findViewById(R.id.submitBtn);
        resendBtn = findViewById(R.id.resendBtn);
        //setting content on views
       // toolbarText.setText(getString(R.string.recover_password));
        apiInterface = APiClient.getClient().create(ApiInterface.class);

        //setting listener
        backBtn.setOnClickListener(this);
        //listener on first lay
        recoverBtn.setOnClickListener(this);
        //listener on second lay
        submitBtn.setOnClickListener(this);
        resendBtn.setOnClickListener(this);

        TextWatcher edit = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (e1.length()==1){
                        e1.clearFocus();
                        e2.requestFocus();
                        e2.setCursorVisible(true);
                    }
                    if (e2.length()==1){
                        e2.clearFocus();
                        e3.requestFocus();
                        e3.setCursorVisible(true);
                    }
                    if (e3.length()==1){
                        e3.clearFocus();
                        e4.requestFocus();
                        e4.setCursorVisible(true);
                    }
                    if (e4.length()==1){
                        e4.clearFocus();
                        newPass.requestFocus();
                    }
                }
            }
        };

        e1.addTextChangedListener(edit);
        e2.addTextChangedListener(edit);
        e3.addTextChangedListener(edit);
        e4.addTextChangedListener(edit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbarBtn:
                finish();
                break;
            case R.id.recoverBtn:
                validation();
                break;
            case R.id.submitBtn:
                resetPassword();
                break;
            case R.id.resendBtn:
                secondLay.setVisibility(View.GONE);
                firstLay.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void resetPassword() {

        if (e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty() || e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty()){
            Toast.makeText(this,"Please Enter OTP Code",Toast.LENGTH_SHORT).show();
        }else if (newPass.getText().toString().isEmpty()){
            newPass.setError(getString(R.string.userPassword_error));
            newPass.requestFocus();
        }else if (newPassCon.getText().toString().isEmpty()){
            newPassCon.setError("Please Enter Confirm Password");
            newPassCon.requestFocus();
        }
        else if (!newPassCon.getText().toString().equals(newPass.getText().toString())){
            newPassCon.setError(getString(R.string.userPassword_con_error));
            newPassCon.requestFocus();
        }
        else{
            String otp_code =e1.getText().toString()+e2.getText().toString()+e3.getText().toString()+e4.getText().toString();
            if (otp_code.length() !=4){
                Toast.makeText(this,"Please Enter OTP Code",Toast.LENGTH_SHORT).show();
            }else {
                DataManager.getInstance().showProgressMessage(this,"Please wait...");
                Map<String,String> map = new HashMap<>();
                map.put("id",id);
                map.put("otp",otp_code);
                map.put("password",newPass.getText().toString());

                Call<UserData> resetCall = apiInterface.resetPass(Constant.AUTH,map);
                resetCall.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        DataManager.getInstance().hideProgressMessage();
                        try {
                            UserData userData = response.body();
                            if (userData.response==200){
                                Toast.makeText(getApplicationContext(),userData.message,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPassActivity.this,LoginActivity.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),userData.message,Toast.LENGTH_SHORT).show();
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
        }

    }

    private void validation() {

        //phone number validation
        if (!userPhone.getMasked().isEmpty()){
            String raw_phone = userPhone.getMasked().split(" ")[1];
            mobile = raw_phone.split("-")[0]+raw_phone.split("-")[1];
        }

            if (userPhone.getMasked().isEmpty()){
                userPhone.setError(getString(R.string.userPhone_error));
                userPhone.requestFocus();
            }else if (mobile.length() !=11){
                userPhone.setError(getString(R.string.userPhone_error_valid));
                userPhone.requestFocus();
            }else if (!mobile.startsWith("0")){
                userPhone.setError(getString(R.string.userPhone_error_number));
                userPhone.requestFocus();
            }
            else {
                DataManager.getInstance().showProgressMessage(ForgetPassActivity.this,"Please Wait...");
                Call<UserData> forgotCall = apiInterface.forgotPass(Constant.AUTH,userPhone.getMasked(),token);
                forgotCall.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        DataManager.getInstance().hideProgressMessage();
                        try {
                            UserData userData = response.body();
                            if (userData.response==200){
                                firstLay.setVisibility(View.GONE);
                                secondLay.setVisibility(View.VISIBLE);
                                txtReset.setText(getText(R.string.reset_msg)+" "+userPhone.getMasked());
                                id = userData.data.id;
                                otpOne = userData.data.resetCode;
                            }else {
                                Toast.makeText(ForgetPassActivity.this,userData.message,Toast.LENGTH_SHORT).show();
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
        }
    }
