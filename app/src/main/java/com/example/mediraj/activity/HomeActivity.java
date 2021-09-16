package com.example.mediraj.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.ServiceAdapter;
import com.example.mediraj.adaptar.imageslider.ImageSliderAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements ServiceAdapter.ServiceInterface {
    public static final String TAG = HomeActivity.class.getName();
    private RecyclerView ser_rec;
    List<String> titles;
    List<Integer> images;
    ServiceAdapter serviceAdapter;
    ServiceAdapter.ServiceInterface serviceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageLoader();
        Locale locale = new Locale("bn");
        Log.e("testing",locale.getCountry()+" ** ");

        serviceInterface = this;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.cart) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.more) {
                startActivity(new Intent(getApplicationContext(), MoreActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        //add item to the list
        titles = new ArrayList<>();
        images = new ArrayList<>();

       // titles.add("Doctor appointment");
        titles.add(getString(R.string.da));
        //titles.add("Diagnostic Service");
        titles.add(getString(R.string.ds));
       // titles.add("Medicine Service");
        titles.add(getString(R.string.medi_service));
        //titles.add("Clinic Service");
        titles.add(getString(R.string.clinic_service));
       // titles.add("BloodBank Service");
        titles.add(getString(R.string.bb));
       // titles.add("Home Pathology");
        titles.add(getString(R.string.hp));
      //  titles.add("Medical Device");
        titles.add(getString(R.string.md));
       // titles.add("Online Doctor");
        titles.add(getString(R.string.online_doctor));

        images.add(R.drawable.ic_doctor1);
        images.add(R.drawable.ic_diagonistic);
        images.add(R.drawable.ic_capsule);
        images.add(R.drawable.ic_patient);
        images.add(R.drawable.ic_blood_bank);
        images.add(R.drawable.ic_pathology_1);
        images.add(R.drawable.ic_surgical1);
        images.add(R.drawable.ic_doctor);


        //initialize views
        ser_rec = findViewById(R.id.sec_rec);
        ser_rec.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ser_rec.setLayoutManager(new GridLayoutManager(this,2));
        ser_rec.setHasFixedSize(true);
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
        }else if (serName.equalsIgnoreCase("Medical Device")){
            Intent intent = new Intent(HomeActivity.this,SurgicalActivity.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Home Pathology")){
            Intent intent = new Intent(HomeActivity.this,HomePathologyActivity.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Doctor appointment")){
            Intent intent = new Intent(HomeActivity.this,DoctorListActivity.class);
            startActivity(intent);
        }else if (serName.equalsIgnoreCase("Online Doctor")){
            Intent intent = new Intent(HomeActivity.this, EmergencyDoctorActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to exit?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }

    //section for image slider api call

    private void imageLoader(){
        int [] img = {R.drawable.mediraj,R.drawable.mediraj_one,R.drawable.mediraj_two};

        SliderView sliderView = findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new ImageSliderAdapter(this,img));
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();


    }
}