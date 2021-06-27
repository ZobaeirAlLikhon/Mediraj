package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.DoctorListAD;
import com.example.mediraj.adaptar.GetDepartmentAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllDepartmentModel;
import com.example.mediraj.model.AllDoctorList;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity {

    ApiInterface apiInterface,apiInterfaceDoctor;
    RecyclerView recyclerView,recyclerViewDoctor;
    List<AllDepartmentModel.Datum> allDepartmentModel;
    List<AllDoctorList.Datum> allDoctorlistModel;
    GetDepartmentAD adapter;
    DoctorListAD adapterDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        //DepartMentList
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.recy_view_depart_horizontal);

        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclar_view();

        //DoctorList
        apiInterfaceDoctor = APiClient.getClient().create(ApiInterface.class);
        recyclerViewDoctor=findViewById(R.id.recy_view_doc_vertical);

        recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclardoc_view();


    }



    //DepartMentList

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
    //DoctorList

    private void recyclardoc_view() {
        Call<AllDoctorList> getDigServices=apiInterface.allDoctorServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllDoctorList>() {
            @Override
            public void onResponse(Call<AllDoctorList> call, Response<AllDoctorList> response) {
                allDoctorlistModel=response.body().getData();
                adapterDoctor=new DoctorListAD(getApplicationContext(),allDoctorlistModel);
                recyclerViewDoctor.setAdapter(adapterDoctor);
                Log.e("getAllDia",String.valueOf(allDoctorlistModel));
            }

            @Override
            public void onFailure(Call<AllDoctorList> call, Throwable t) {

            }
        });
    }
}
