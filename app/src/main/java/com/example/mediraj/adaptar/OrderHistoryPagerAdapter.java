package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediraj.fragment.order.BloodBankFragment;
import com.example.mediraj.fragment.order.ClinicOrderFragment;
import com.example.mediraj.fragment.order.DiagnosticOrderFragment;
import com.example.mediraj.fragment.order.DoctorFragment;
import com.example.mediraj.fragment.order.MedicineFragment;
import com.example.mediraj.fragment.order.PathologyOrderFragment;
import com.example.mediraj.fragment.order.SurgicalOrderFragment;

import org.jetbrains.annotations.NotNull;

public class OrderHistoryPagerAdapter extends FragmentPagerAdapter {
    private int noOfTabs;
    private Context context;

    public OrderHistoryPagerAdapter(FragmentManager fm, int noOfTabs, Context context) {
        super(fm);
        this.noOfTabs = noOfTabs;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DoctorFragment doctorFragment = new DoctorFragment();
                return doctorFragment;
            case 1:
                DiagnosticOrderFragment diagnosticOrderFragment = new DiagnosticOrderFragment();
                return diagnosticOrderFragment;
            case 2:
                MedicineFragment medicineFragment = new MedicineFragment();
                return medicineFragment;
            case 3:
                ClinicOrderFragment clinicOrderFragment = new ClinicOrderFragment();
                return clinicOrderFragment;
            case 4:
                BloodBankFragment bloodBankFragment = new BloodBankFragment();
                return bloodBankFragment;
            case 5:
                SurgicalOrderFragment surgicalOrderFragment = new SurgicalOrderFragment();
                return surgicalOrderFragment;
            case 6:
                PathologyOrderFragment pathologyOrderFragment = new PathologyOrderFragment();
                return pathologyOrderFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
