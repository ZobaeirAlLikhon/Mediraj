package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.GetDepartmentAD;
import com.example.mediraj.adaptar.Get_pathologyAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllDepartmentModel;
import com.example.mediraj.model.AllPathologyModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllDepartmentModel.Datum> allDepartmentModel;
    GetDepartmentAD adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.recy_view_depart_horizontal);

        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclar_view();
    }

    private void recyclar_view() {
        Call<AllDepartmentModel> getDigServices=apiInterface.allDepartmentServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllDepartmentModel>() {
            @Override
            public void onResponse(Call<AllDepartmentModel> call, Response<AllDepartmentModel> response) {
                allDepartmentModel=response.body().getData();
                adapter=new GetDepartmentAD(getApplicationContext(),allDepartmentModel);
                recyclerView.setAdapter(adapter);
                Log.e("getAllDia",String.valueOf(allDepartmentModel));
            }

            @Override
            public void onFailure(Call<AllDepartmentModel> call, Throwable t) {

            }
        });
    }
}
