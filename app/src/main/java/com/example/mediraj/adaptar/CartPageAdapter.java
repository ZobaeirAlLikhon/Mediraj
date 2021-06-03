package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediraj.fragment.ClinicFragment;
import com.example.mediraj.fragment.PathologyFragment;
import com.example.mediraj.fragment.SurgicalFragment;

public class CartPageAdapter extends FragmentPagerAdapter {
    private int noOfTabs;
    private Context context;

    public CartPageAdapter(FragmentManager fm, int noOfTabs, Context context) {
        super(fm);
        this.noOfTabs = noOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ClinicFragment clinicFragment = new ClinicFragment();
                return clinicFragment;
            case 1:
                PathologyFragment pathologyFragment = new PathologyFragment();
                return pathologyFragment;
            case 2:
                SurgicalFragment surgicalFragment = new SurgicalFragment();
                return surgicalFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
