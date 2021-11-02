package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.CartPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CartActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    String tabIndex = null;
    String [] title = new String[]{"Diagnostic","Pathology","Medical Device"};
    int [] icons = new int[]{R.drawable.ic_diagonistic,R.drawable.ic_pathology_1,R.drawable.ic_surgical1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.cart) {
                return true;
            } else if (itemId == R.id.history) {
                startActivity(new Intent(this, OrderActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.more) {
                startActivity(new Intent(getApplicationContext(), MoreActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }

            return false;
        });

    }

    private void initView() {



        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        CartPageAdapter pagerAdapter = new CartPageAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> {
            tab.setText(title[position]);
            tab.setIcon(icons[position]);
        }).attach();

        try {
            Intent intent = getIntent();
            if (intent != null) {
                tabIndex = getIntent().getStringExtra("index");
                Log.e("data", tabIndex + "");
                if (tabIndex!=null){
                    switchToTab (tabIndex);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToTab(String tab) {
        switch (tab) {
            case "1":
                viewPager.setCurrentItem(1);
                tabLayout.getTabAt(0).select();
                break;
            case "2":
                viewPager.setCurrentItem(2);
                tabLayout.getTabAt(1).select();
                break;
            case "3":
                viewPager.setCurrentItem(3);
                tabLayout.getTabAt(2).select();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != selectedItemId) {
            bottomNavigationView.setSelectedItemId(R.id.home);
            finish();
        }
    }


}