package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.ServiceAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements ServiceAdapter.ServiceInterface {
    public static final String TAG = HomeActivity.class.getName();
    private ImageView bannerImg;
    private RecyclerView ser_rec;
    List<String> titles;
    List<Integer> images;
    ServiceAdapter serviceAdapter;
    ServiceAdapter.ServiceInterface serviceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceInterface = this;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.more:
                        startActivity(new Intent(getApplicationContext(),MoreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        //add item to the list
        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Doctor appoinment");
        titles.add("Diagnostic Service");
        titles.add("Medicine Service");
        titles.add("Clinic Service");
        titles.add("BloodBank Service");
        titles.add("Home Pathology");
        titles.add("Surgical Kits");
        titles.add("Doctor");

        images.add(R.drawable.doctorapp2);
        images.add(R.drawable.ic_appoint);
        images.add(R.drawable.medicin);
        images.add(R.drawable.clinic1);
        images.add(R.drawable.blood);
        images.add(R.drawable.pathology);
        images.add(R.drawable.kits);
        images.add(R.drawable.ic_appoint);


        //initialize views
        bannerImg = findViewById(R.id.bannerImg);
        ser_rec = findViewById(R.id.sec_rec);
        ser_rec.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ser_rec.setLayoutManager(new GridLayoutManager(this,2));
        serviceAdapter = new ServiceAdapter(this,titles,images,serviceInterface);
        ser_rec.setAdapter(serviceAdapter);
    }


    @Override
    public void onClickInterface(String serName) {
        Log.e(TAG,serName);
        if (serName.equalsIgnoreCase("Medicine Service")){
            Intent intent = new Intent(HomeActivity.this,MedicineService.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Clinic Service")){
            Intent intent = new Intent(HomeActivity.this,ClinicService.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("BloodBank Service")){
            Intent intent = new Intent(HomeActivity.this,BloodbankService.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Diagnostic Service")){
            Intent intent = new Intent(HomeActivity.this,DiagnosticActivity.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Surgical Kits")){
            Intent intent = new Intent(HomeActivity.this,SurgicalActivity.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Home Pathology")){
            Intent intent = new Intent(HomeActivity.this,HomePathologyActivity.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Doctor appoinment")){
            Intent intent = new Intent(HomeActivity.this,DoctorListActivity.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Doctor")){
            Intent intent = new Intent(HomeActivity.this,Emergency_DoctorActivity.class);
            startActivity(intent);
        }
    }
}