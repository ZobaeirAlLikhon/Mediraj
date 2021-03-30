package com.example.mediraj.fragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;

public class Login_fragment extends Fragment implements View.OnClickListener {


    View view;
    TextInputEditText logEmail, logPass;
    MaterialButton loginBtn;
    String userEmail, userPass;
    ApiInterface apiInterface;

    public Login_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_fragment, container, false);
        initView();

        return view;
    }

    private void initView() {

        //views
        logEmail = view.findViewById(R.id.loginEmail);
        logPass = view.findViewById(R.id.loginPass);
        loginBtn = view.findViewById(R.id.btnLogin);
        //api interface
        apiInterface = APiClient.getClient().create(ApiInterface.class);

        //listener for views
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {

        if (logEmail.getText().toString().isEmpty()) {
            logEmail.setError(getString(R.string.userEmail_error));
            logEmail.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(logEmail.getText().toString()).matches()) {
            logEmail.setError(getString(R.string.userEmail_error_valid));
            logEmail.requestFocus();
        }
        else if (logPass.getText().toString().isEmpty()) {
            logPass.setError(getString(R.string.userPassword_error));
            logPass.requestFocus();
        } else {
            if (ConnectionManager.connection(getContext())) {

                //1.show progress loader
                //2.call login api
                //3.hide loader after response or failure
                //4.if get response then write it to shared preference or roomDb
                //5.show toast message switch to home activity
                //6.if fail then show error message

            }else {
                Toasty.info(getContext(),getString(R.string.internet_connect_msg),Toasty.LENGTH_SHORT).show();
            }
        }
    }
}