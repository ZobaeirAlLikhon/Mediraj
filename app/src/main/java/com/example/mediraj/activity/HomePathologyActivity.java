package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.HomePathologyAdapter;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllPathologyModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePathologyActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllPathologyModel.Datum> allPathologyModel;
    HomePathologyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pathology);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.recy_view_pathology);

        recyclerView.setLayoutManager(new LinearLayoutManager(HomePathologyActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclar_view();
    }

    private void recyclar_view() {
        Call<AllPathologyModel> getDigServices=apiInterface.allPathologyServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllPathologyModel>() {
            @Override
            public void onResponse(Call<AllPathologyModel> call, Response<AllPathologyModel> response) {
                allPathologyModel=response.body().getData();
                adapter=new HomePathologyAdapter(getApplicationContext(),allPathologyModel);
                recyclerView.setAdapter(adapter);
                Log.e("getAllDia",String.valueOf(allPathologyModel));
            }

            @Override
            public void onFailure(Call<AllPathologyModel> call, Throwable t) {

            }
        });
    }

}