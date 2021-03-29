package com.example.mediraj.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mediraj.R;
import com.example.mediraj.activity.Login;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.Example;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp_fragment extends Fragment {
    View sign_up_view;
    Context context;
    TextInputEditText user_name,email,password,password_conf,phone;
    MaterialButton sign_upBT;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String nameET,emailET,passET,con_passET,phoneET;
    ApiInterface apiInterface;

    public SignUp_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sign_up_view= inflater.inflate(R.layout.fragment_sign_up_fragment, container, false);
        init();
        return sign_up_view;
    }

    private void init() {
        user_name=sign_up_view.findViewById(R.id.userName);
        email=sign_up_view.findViewById(R.id.userEmail);
        phone=sign_up_view.findViewById(R.id.userPhone);
        password=sign_up_view.findViewById(R.id.regPass);
        password_conf=sign_up_view.findViewById(R.id.regConPass);
        sign_upBT=sign_up_view.findViewById(R.id.btnSignUp);

        sign_upBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up_con();
            }
        });
    }

    private void input_data() {


    }

    private void sign_up_con() {
        nameET=user_name.getText().toString().trim();
        emailET=email.getText().toString().trim();
        phoneET=phone.getText().toString().trim();
        passET=password.getText().toString().trim();
        con_passET=password_conf.getText().toString().trim();
        if(nameET.isEmpty())
        {
            Toast.makeText(getContext(),"Enter Your Name",Toast.LENGTH_SHORT).show();
        }
        else if(emailET.isEmpty())
        {
            Toast.makeText(getContext(),"Enter Your Email",Toast.LENGTH_SHORT).show();
        }
        else if(phoneET.isEmpty())
        {
            Toast.makeText(getContext(),"Enter Your Phone",Toast.LENGTH_SHORT).show();
        }
        else if(passET.isEmpty())
        {
            Toast.makeText(getContext(),"Enter Your password",Toast.LENGTH_SHORT).show();
        }
        else if(!con_passET.equals(password.getText().toString().trim())){
            Toast.makeText(getContext(),"Password Not matching",Toast.LENGTH_SHORT).show();

        }
        else{
//            if(!email.getText().toString().trim().matches(emailPattern))
//            {
//                Toast.makeText(getContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
//            }
            apiInterface= APiClient.getClient().create(ApiInterface.class);
            Call<Example> call=apiInterface.sign_up_profile(Constant.api_key,Constant.auth,nameET,emailET,phoneET,con_passET);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    Log.e("sign_Up",response.message().toString());
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Log.e("sign_Up",t.toString());
                }
            });
        }
    }
}