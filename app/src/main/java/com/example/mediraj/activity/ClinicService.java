package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.ClinicServicesAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.ClinicalModel;
import com.example.mediraj.model.UserData;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicService extends AppCompatActivity {
    ApiInterface apiInterface;
    List<ClinicalModel.Datum> clinicalModelList;
    RecyclerView recyclerView;
    ClinicServicesAD clinicServicesAD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_service);
        recyclerView=findViewById(R.id.recy_view_clinic);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClinicService.this,LinearLayoutManager.VERTICAL,false));
        recyclar_view();

    }

    private void recyclar_view() {
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        Call<ClinicalModel> clinical_services_call = apiInterface.clinicalServices(Constant.AUTH);
        clinical_services_call.enqueue(new Callback<ClinicalModel>() {
            @Override
            public void onResponse(Call<ClinicalModel> call, Response<ClinicalModel> response) {
                clinicalModelList=response.body().getData();
                clinicServicesAD=new ClinicServicesAD(getApplicationContext(),clinicalModelList);
                recyclerView.setAdapter(clinicServicesAD);
                Log.e("clinicalServices:----",clinicalModelList.toString());
            }

            @Override
            public void onFailure(Call<ClinicalModel> call, Throwable t) {

            }
        });
    }
}