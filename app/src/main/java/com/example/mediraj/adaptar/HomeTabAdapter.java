package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediraj.fragment.Popular_fragment;
import com.example.mediraj.fragment.Service_fragment;

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
                Service_fragment service_fragment = new Service_fragment();
                return service_fragment;
            case 1:
                Popular_fragment popular_fragment = new Popular_fragment();
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
