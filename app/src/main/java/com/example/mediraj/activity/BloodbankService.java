package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.Get_BloodAD;
import com.example.mediraj.adaptar.Get_pathologyAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllPathologyModel;
import com.example.mediraj.model.AllbloodModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodbankService extends AppCompatActivity {

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllbloodModel.Datum> allbloodModel;
    Get_BloodAD adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank_service);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.recy_view_blood);

        recyclerView.setLayoutManager(new LinearLayoutManager(BloodbankService.this,LinearLayoutManager.VERTICAL,false));
        recyclar_view();
    }

    private void recyclar_view() {
        Call<AllbloodModel> getDigServices=apiInterface.allBloodServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllbloodModel>() {
            @Override
            public void onResponse(Call<AllbloodModel> call, Response<AllbloodModel> response) {
                allbloodModel=response.body().getData();
                adapter=new Get_BloodAD(getApplicationContext(),allbloodModel);
                recyclerView.setAdapter(adapter);
                Log.e("getAllDia",String.valueOf(allbloodModel));
            }

            @Override
            public void onFailure(Call<AllbloodModel> call, Throwable t) {

            }
        });
    }
}
