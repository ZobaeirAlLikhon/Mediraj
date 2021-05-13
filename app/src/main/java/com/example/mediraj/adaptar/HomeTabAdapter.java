package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediraj.fragment.PopularFragment;
import com.example.mediraj.fragment.ServiceFragment;

public class HomeTabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTab;

    public HomeTabAdapter(Context context, FragmentManager fm, int totalTab) {
        super(fm);
        this.context = context;
        this.totalTab = totalTab;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ServiceFragment service_fragment = new ServiceFragment();
                return service_fragment;
            case 1:
                PopularFragment popular_fragment = new PopularFragment();
                return popular_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
