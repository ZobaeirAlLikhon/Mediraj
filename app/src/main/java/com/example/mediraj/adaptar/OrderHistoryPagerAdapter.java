package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediraj.fragment.BloodBankFragment;
import com.example.mediraj.fragment.ClinicFragment;
import com.example.mediraj.fragment.ClinicOrderFragment;
import com.example.mediraj.fragment.DiagnosticFragment;
import com.example.mediraj.fragment.DoctorFragment;
import com.example.mediraj.fragment.MedicineFragment;
import com.example.mediraj.fragment.PathologyFragment;
import com.example.mediraj.fragment.PathologyOrderFragment;
import com.example.mediraj.fragment.SurgicalFragment;
import com.example.mediraj.fragment.SurgicalOrderFragment;

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
                DiagnosticFragment diagnosticFragment = new DiagnosticFragment();
                return diagnosticFragment;
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
