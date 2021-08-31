package com.example.mediraj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.ClinicServicesAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.ClinicalModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicService extends AppCompatActivity {
    ApiInterface apiInterface;
    ClinicalModel clinicalModelList;
    RecyclerView recyclerView;
    ClinicServicesAdapter clinicServicesAdapter;
    ImageView toolbarBtn;
    TextView toolbarTxt,noData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_service);


        initView();

        if (ConnectionManager.connection(this)){
             recyclerView();
        }else {
            Toast.makeText(this, R.string.internet_connect_msg, Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {

        recyclerView=findViewById(R.id.recy_view_clinic);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClinicService.this,LinearLayoutManager.VERTICAL,false));
        toolbarBtn = findViewById(R.id.toolbarBtn);
        toolbarTxt = findViewById(R.id.toolbarText);
        toolbarTxt.setText(R.string.clinic_service);
        noData = findViewById(R.id.noData);

        toolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void recyclerView() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        Call<ClinicalModel> clinical_services_call = apiInterface.clinicalServices(Constant.AUTH);
        clinical_services_call.enqueue(new Callback<ClinicalModel>() {
            @Override
            public void onResponse(@NonNull Call<ClinicalModel> call, @NonNull Response<ClinicalModel> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    clinicalModelList=response.body();
                    if (clinicalModelList.getResponse()==200){
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        clinicServicesAdapter =new ClinicServicesAdapter(getApplicationContext(),clinicalModelList.getData());
                        recyclerView.setAdapter(clinicServicesAdapter);
                    }else{
                        recyclerView.setVisibility(View.INVISIBLE);
                        noData.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ClinicalModel> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
            }
        });
    }
}