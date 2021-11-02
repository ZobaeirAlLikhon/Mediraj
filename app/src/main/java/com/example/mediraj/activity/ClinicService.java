package com.example.mediraj.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
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
    TextView noData;
    List<ClinicalModel.Datum> dataList = new ArrayList<>();
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
        noData = findViewById(R.id.noData);
        toolbarBtn.setOnClickListener(v -> finish());

        EditText test = findViewById(R.id.searchBox);
        test.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterList(editable.toString());
            }
        });
    }

    private void filterList(String text) {
        List<ClinicalModel.Datum> filteredList = new ArrayList<>();
        Log.e("data list 1",dataList.toString()+"\n"+dataList.size());
        for (ClinicalModel.Datum item:dataList){
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
                Log.e("filter item",filteredList.toString());
            }
        }

        clinicServicesAdapter.searchList(filteredList);
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
                        dataList.addAll(clinicalModelList.getData());
                        Log.e("data list",dataList.toString()+"\n"+dataList.size());
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        clinicServicesAdapter =new ClinicServicesAdapter(getApplicationContext(),dataList);
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