package com.example.mediraj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mediraj.R;
import com.example.mediraj.activity.ForgetPassActivity;
import com.example.mediraj.activity.HomeActivity;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.helper.SessionManager;
import com.example.mediraj.model.UserData;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getName();
    ApiInterface apiInterface;
    View view;
    TextInputEditText pass;
    MaskEditText phone;
    MaterialButton loginBtn;
    TextView forgotPass;
    String mobile,token;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.login_fragment, container, false);
        initView();

        return view;
    }

    private void initView() {

        //views
        phone = view.findViewById(R.id.userPhone);
        pass = view.findViewById(R.id.loginPass);
        loginBtn = view.findViewById(R.id.btnLogin);
        forgotPass = view.findViewById(R.id.forgotPass);
        //api interface
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        //listener for views
        loginBtn.setOnClickListener(this);
        forgotPass.setOnClickListener(this);

        token = FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                validation();
                break;
            case R.id.forgotPass:
                startActivity(new Intent(getContext(), ForgetPassActivity.class));
                break;
        }
    }

    private void validation() {
        //extract phone number from field
        if (!phone.getMasked().equals("")) {
            String raw_phone = phone.getMasked().split(" ")[1];
            mobile = raw_phone.split("-")[0] + raw_phone.split("-")[1];
        }


        if (phone.getMasked().isEmpty()) {
            phone.setError(getString(R.string.userPhone_error));
            phone.requestFocus();
        }else if (mobile.length() !=11) {
            phone.setError(getString(R.string.userPhone_error_valid));
            phone.requestFocus();
        }else if (!mobile.startsWith("0")){
            phone.setError(getString(R.string.userPhone_error_number));
        }
        else if (pass.getText().toString().isEmpty()) {
            pass.setError(getString(R.string.userPassword_error));
            pass.requestFocus();
        } else {
            if (ConnectionManager.connection(getContext())) {
                    userLogin();
            }else {
                Toast.makeText(getContext(),getString(R.string.internet_connect_msg),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void userLogin() {
        DataManager.getInstance().showProgressMessage(getActivity(),"Please Wait...");
        Map<String,String> map = new HashMap<>();
        map.put("mobile",phone.getMasked());
        map.put("password",pass.getText().toString());
        map.put("token",token);

        Log.e(TAG,map.toString());

       Call<UserData> loginCall = apiInterface.userLogIn(Constant.AUTH,map);
       loginCall.enqueue(new Callback<UserData>() {
           @Override
           public void onResponse(Call<UserData> call, Response<UserData> response) {
               DataManager.getInstance().hideProgressMessage();

               try {
                   UserData userData = response.body();
                   if (userData.response==200){
                       String dataResponse = new Gson().toJson(response.body());
                       Log.e(TAG,"Login response : "+dataResponse);
                       SessionManager.writeString(getContext(),Constant.USER_INFO,dataResponse);
                       Toast.makeText(getContext(),userData.message,Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getContext(), HomeActivity.class));
                       getActivity().finish();
                   }else {
                       Toast.makeText(getContext(), userData.message, Toast.LENGTH_SHORT).show();
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