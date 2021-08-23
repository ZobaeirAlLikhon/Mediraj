package com.example.mediraj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.ClinicServicesAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.ClinicalModel;
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
    ImageView toolbarBtn;
    TextView toolbarTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
       // recyclerView();

    }

    private void initView() {
        setContentView(R.layout.activity_clinic_service);
        recyclerView=findViewById(R.id.recy_view_clinic);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClinicService.this,LinearLayoutManager.VERTICAL,false));
        toolbarBtn = findViewById(R.id.toolbarBtn);
        toolbarTxt = findViewById(R.id.toolbarText);
        toolbarTxt.setText("Clinic Services");

        toolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0,0);
                finish();
            }
        });
    }

    private void recyclerView() {
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        Call<ClinicalModel> clinical_services_call = apiInterface.clinicalServices(Constant.AUTH);
        clinical_services_call.enqueue(new Callback<ClinicalModel>() {
            @Override
            public void onResponse(Call<ClinicalModel> call, Response<ClinicalModel> response) {
                clinicalModelList=response.body().getData();
                clinicServicesAD=new ClinicServicesAD(getApplicationContext(),clinicalModelList);
                recyclerView.setAdapter(clinicServicesAD);
               // Log.e("clinicalServices:----",clinicalModelList.toString());
            }

            @Override
            public void onFailure(Call<ClinicalModel> call, Throwable t) {

            }
        });
    }
}