package com.example.mediraj.fragment;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp_fragment extends Fragment implements View.OnClickListener {

    private static final String TAG ="Sign_up_fragment" ;
    //tabLayout
    TabLayout tabLayout;
    //fragment views
    View view;
    TextInputEditText user_name, email, password, password_conf;
    MaskEditText phone;
    MaterialButton signUpBtn;
    String nameET, emailET, passET, con_passET, phoneET;
    ApiInterface apiInterface;

    public SignUp_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up_fragment, container, false);
        init();
        return view;
    }

    private void init() {

        //initialize all view from sign_up_fragment
        user_name = view.findViewById(R.id.userName);
        email = view.findViewById(R.id.userEmail);
        phone = view.findViewById(R.id.userPhone);
        password = view.findViewById(R.id.regPass);
        password_conf = view.findViewById(R.id.regConPass);
        signUpBtn = view.findViewById(R.id.btnSignUp);

        //initialize view from paren
        tabLayout = getActivity().findViewById(R.id.tabLayout);


        //api interface initialize
        apiInterface = APiClient.getClient().create(ApiInterface.class);


        //set on_click_listener
        signUpBtn.setOnClickListener(this);
    }

    private void sign_up_con() {

        //extract phone number from field
        if (!phone.getMasked().equals("")) {
            String raw_phone = phone.getMasked().split(" ")[1];
            phoneET = raw_phone.split("-")[0] + raw_phone.split("-")[1];

        }


        nameET = user_name.getText().toString().trim();
        emailET = email.getText().toString().trim();
        passET = password.getText().toString().trim();
        con_passET = password_conf.getText().toString().trim();


        if (nameET.isEmpty()) {
            user_name.setError(getString(R.string.userName_error));
            user_name.requestFocus();
        } else if (emailET.isEmpty()) {
            email.setError(getString(R.string.userEmail_error));
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailET).matches()) {
            email.setError(getString(R.string.userEmail_error_valid));
            email.requestFocus();
        } else if (phone.getMasked().isEmpty()) {
            phone.setError(getString(R.string.userPhone_error));
            phone.requestFocus();
        } else if (phoneET.length() != 11) {
            phone.setError(getString(R.string.userPhone_error_valid));
            phone.requestFocus();
        } else if (!phoneET.startsWith("0")) {
            phone.setError(getString(R.string.userPhone_error_number));
            phone.requestFocus();
        } else if (passET.isEmpty()) {
            password.setError(getString(R.string.userPassword_error));
            password.requestFocus();
        } else if (!con_passET.equals(password.getText().toString().trim())) {
            password_conf.setError(getString(R.string.userPassword_con_error));
            password_conf.requestFocus();
        }else {
            if (ConnectionManager.connection(getContext())){
                DataManager.getInstance().showProgressMessage(getActivity(),"Please Wait...");
                Map<String,String> map = new HashMap<>();
                map.put("name",nameET);
                map.put("mobile",phoneET);
                map.put("email",emailET);
                map.put("password",passET);
                Log.e("User Data",map.toString());
                Call<Map<String,String>> call = apiInterface.userSignUp(Constant.api_key,Constant.auth,map);
                call.enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        DataManager.getInstance().hideProgressMessage();
                        try {
                            Map<String,String> data = response.body();
                            if (data.get("response").equals("200")){
                                //for new user
                                Toasty.success(getContext(),data.get("message"),Toasty.LENGTH_SHORT).show();
                                tabLayout.newTab().select();
                            }else if (data.get("response").equals("some response")){
                                //for existing user
                                //TODO fixed api response
                                Toasty.info(getContext(),data.get("message"),Toasty.LENGTH_SHORT).show();
                                tabLayout.newTab().select();
                            }
                            else {
                                //for any error
                                Toasty.error(getContext(),data.get("message"),Toasty.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        DataManager.getInstance().hideProgressMessage();
                        Log.e(TAG,t.toString());
                        Toasty.error(getContext(),getString(R.string.error_api),Toasty.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toasty.info(getContext(),getString(R.string.internet_connect_msg),Toasty.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                sign_up_con();
                break;
        }
    }
}