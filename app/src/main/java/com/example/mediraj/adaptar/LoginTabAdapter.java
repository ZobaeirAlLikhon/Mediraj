package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediraj.fragment.Login_fragment;
import com.example.mediraj.fragment.Popular_fragment;
import com.example.mediraj.fragment.Service_fragment;
import com.example.mediraj.fragment.SignUp_fragment;

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
                Login_fragment login_fragment = new Login_fragment();
                return login_fragment;
            case 1:
                SignUp_fragment signUp_fragment = new SignUp_fragment();
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