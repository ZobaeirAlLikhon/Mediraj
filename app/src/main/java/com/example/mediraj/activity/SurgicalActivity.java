package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.Get_SurgicalAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllSurgicalModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurgicalActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllSurgicalModel.Datum> allSurgicalModel;
    Get_SurgicalAD adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgical);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.recy_view_surgical);
        recyclerView.setLayoutManager(new LinearLayoutManager(SurgicalActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclar_view();

    }

    private void recyclar_view() {
        Call<AllSurgicalModel> getDigServices=apiInterface.allSurgicalServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllSurgicalModel>(){

            @Override
            public void onResponse(Call<AllSurgicalModel> call, Response<AllSurgicalModel> response) {
                allSurgicalModel=response.body().getData();
                adapter=new Get_SurgicalAD(getApplicationContext(),allSurgicalModel);
                recyclerView.setAdapter(adapter);
                Log.e("getAllDia..",allSurgicalModel.get(0).getTitle());

            }


            @Override
            public void onFailure(Call<AllSurgicalModel> call, Throwable t) {

            }
        });
    }
}