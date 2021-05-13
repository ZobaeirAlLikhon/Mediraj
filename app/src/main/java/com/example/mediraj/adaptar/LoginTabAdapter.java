package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediraj.fragment.LoginFragment;
import com.example.mediraj.fragment.SignUpFragment;

public class LoginTabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTab;

    public LoginTabAdapter(Context context, FragmentManager fm, int totalTab) {
        super(fm);
        this.context = context;
        this.totalTab = totalTab;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginFragment login_fragment = new LoginFragment();
                return login_fragment;
            case 1:
                SignUpFragment signUp_fragment = new SignUpFragment();
                return signUp_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}