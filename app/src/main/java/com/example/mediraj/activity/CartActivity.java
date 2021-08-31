package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.mediraj.R;
import com.example.mediraj.adaptar.CartPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;

public class CartActivity extends AppCompatActivity {

    private  TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

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
                return true;
            } else if (itemId == R.id.cart) {
                return true;
            } else if (itemId == R.id.more) {
                startActivity(new Intent(getApplicationContext(), MoreActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        CartPageAdapter pagerAdapter = new CartPageAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try {
            Intent intent = getIntent();
            if (intent !=null) {
                String tab = getIntent().getStringExtra("index");
                if (tab!=null){
                    switchToTab(tab);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToTab(String tab){
        switch (tab) {
            case "":
                Log.e("no data", "no data found");
                break;
            case "1":
                viewPager.setCurrentItem(0);
                break;
            case "2":
                viewPager.setCurrentItem(1);
                break;
            case "3":
                viewPager.setCurrentItem(2);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != selectedItemId) {
           bottomNavigationView.setSelectedItemId(R.id.home);
        }
        else {
            super.onBackPressed();
        }
    }


}