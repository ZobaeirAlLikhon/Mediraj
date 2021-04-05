package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;

public class ForgetPass extends AppCompatActivity implements View.OnClickListener {

    //firstLay
    TextView toolbarText;
    ImageView toolbarBtn;
    ApiInterface apiInterface;
    TextInputEditText recoverEmail;
    MaterialButton recoverBtn;
    RelativeLayout firstLay;

    //secondLay
    AppCompatButton openEmail,resetBtn;
    TextView reEnterEmail;
    RelativeLayout secondLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        initView();
    }

    private void initView() {
        //views
        //first
        toolbarText = findViewById(R.id.toolbarText);
        toolbarBtn = findViewById(R.id.toolbarBtn);
        recoverEmail = findViewById(R.id.recoverEmail);
        recoverBtn = findViewById(R.id.recoverBtn);
        firstLay = findViewById(R.id.firstLay);
        //second
        openEmail = findViewById(R.id.openEmail);
        reEnterEmail = findViewById(R.id.reEnterMail);
        secondLay = findViewById(R.id.secondLay);
        resetBtn = findViewById(R.id.resetBtn);

        //setting content on views
        toolbarText.setText(getString(R.string.recover_password));
//        resetCode.getBackground().mutate().setColorFilter(getResources().getColor(R.color.denim), PorterDuff.Mode.SRC_ATOP); //change edit text border color
        apiInterface = APiClient.getClient().create(ApiInterface.class);

        //setting listener
        toolbarBtn.setOnClickListener(this);
        recoverEmail.setOnClickListener(this);
        openEmail.setOnClickListener(this);
        reEnterEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbarBtn:
                onBackPressed();
                finish();
                break;
            case R.id.recoverBtn:
                validation();
                break;
            case R.id.reEnterMail:
                secondLay.setVisibility(View.INVISIBLE);
                firstLay.setVisibility(View.VISIBLE);
                break;
            case R.id.openEmail:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(intent);
                break;
        }
    }

    private void validation() {
        if (recoverEmail.getText().toString().isEmpty()){
            recoverEmail.setError(getString(R.string.userEmail_error));
            recoverEmail.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(recoverEmail.getText().toString()).matches()){
            recoverEmail.setError(getString(R.string.userEmail_error_valid));
            recoverEmail.requestFocus();
        }else {
            if (ConnectionManager.connection(getApplicationContext())){
                //api call for recover pass
                //if response ok then hide firstLay and shows
                //if input is valid then hide secondLay and show thirdLay
                //after reset go back to login page
            }else {
                Toasty.info(ForgetPass.this,"Please Connect to Internet",Toasty.LENGTH_SHORT).show();
            }
        }
    }
}