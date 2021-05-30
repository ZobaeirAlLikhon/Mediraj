package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.Get_diagonesticServicesAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllDiagonosticModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosticActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllDiagonosticModel.Datum> allDiagonosticModel;
    Get_diagonesticServicesAD adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.recy_view_diagnostic);
        recyclerView.setLayoutManager(new LinearLayoutManager(DiagnosticActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclar_view();
    }

    private void recyclar_view() {
        Call<AllDiagonosticModel> getDigServices=apiInterface.allDiagonsticServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllDiagonosticModel>() {
            @Override
            public void onResponse(Call<AllDiagonosticModel> call, Response<AllDiagonosticModel> response) {
                allDiagonosticModel=response.body().getData();
                adapter=new Get_diagonesticServicesAD(getApplicationContext(),allDiagonosticModel);
                recyclerView.setAdapter(adapter);
                Log.e("getAllDia",String.valueOf(allDiagonosticModel));
            }

            @Override
            public void onFailure(Call<AllDiagonosticModel> call, Throwable t) {

            }
        });

    }
}