package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mediraj.fragment.history.BloodRequestFragment;
import com.example.mediraj.fragment.history.ClinicOrderFragment;
import com.example.mediraj.fragment.history.DeviceOrderFragment;
import com.example.mediraj.fragment.history.DiagnosticOrderFragment;
import com.example.mediraj.fragment.history.DoctorOrderFragment;
import com.example.mediraj.fragment.history.MedicineOrderFragment;
import com.example.mediraj.fragment.history.PathologyOrderFragment;

public class OrderPageAdapter extends FragmentStateAdapter {

    public OrderPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new MedicineOrderFragment();
            case 1:
                return new DoctorOrderFragment();
            case 2:
                return new DiagnosticOrderFragment();
            case 3:
                return new ClinicOrderFragment();
            case 4:
                return new BloodRequestFragment();
            case 5:
                return new PathologyOrderFragment();
            case 6:
                return new DeviceOrderFragment();
        }

        return new MedicineOrderFragment();
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
