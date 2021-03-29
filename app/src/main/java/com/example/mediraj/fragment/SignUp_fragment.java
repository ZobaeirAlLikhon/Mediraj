package com.example.mediraj.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.mediraj.R;
import com.example.mediraj.activity.Login;
import com.example.mediraj.adaptar.LoginTabAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;


public class SignUp_fragment extends Fragment {

    MaterialButton btn;
    TabLayout tabLayout;
    public SignUp_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_fragment, container, false);

        btn = view.findViewById(R.id.btnSignUp);
        tabLayout = (TabLayout)((Login)getActivity()).findViewById(R.id.tabLayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    tabLayout.newTab().select();
            }
        });
        return view;
    }


}