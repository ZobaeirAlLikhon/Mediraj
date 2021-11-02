package com.example.mediraj.adaptar;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mediraj.fragment.cart.DiagnosticFragment;
import com.example.mediraj.fragment.cart.PathologyFragment;
import com.example.mediraj.fragment.cart.SurgicalFragment;

public class CartPageAdapter extends FragmentStateAdapter {
    String [] title = new String[]{"Diagnostic","Pathology","Medical Device"};

    public CartPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new DiagnosticFragment();
        } else if (position == 1) {
            return new PathologyFragment();

        } else if (position == 2) {
            return new SurgicalFragment();
        }
        return new DiagnosticFragment();
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
