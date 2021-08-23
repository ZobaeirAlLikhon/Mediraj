package com.example.mediraj.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.DoctorListAD;
import com.example.mediraj.adaptar.GetDepartmentAD;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllDepartmentModel;
import com.example.mediraj.model.AllDoctorList;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity implements View.OnClickListener, DoctorListAD.OnDocListClick {

    ApiInterface apiInterface;
    RecyclerView recyclerView,recyclerViewDoctor;
    AllDepartmentModel allDepartmentModel;
    List<AllDoctorList.Datum> allDoctorlistModel;
    GetDepartmentAD adapter;
    DoctorListAD adapterDoctor;
    ImageView ivBack;
    TextView toolbarTxt;
    DoctorListAD.OnDocListClick onDocListClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        initView();

        if (ConnectionManager.connection(this)){
            recyclar_view();
            recyclardoc_view();
        }else{
            Toast.makeText(this, R.string.internet_connect_msg, Toast.LENGTH_SHORT).show();
        }









    }

    private void initView() {
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        onDocListClick = this;
        ivBack = findViewById(R.id.toolbarBtn);
        toolbarTxt = findViewById(R.id.toolbarText);
        toolbarTxt.setText(R.string.doctorlist);
        recyclerViewDoctor=findViewById(R.id.recy_view_doc_vertical);
        recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclerView=findViewById(R.id.recy_view_depart_horizontal);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this,LinearLayoutManager.HORIZONTAL,false));


        ivBack.setOnClickListener(this);
    }


    //DepartmentList

    private void recyclar_view() {
        Call<AllDepartmentModel> call = apiInterface.allDepartment(Constant.AUTH);
        call.enqueue(new Callback<AllDepartmentModel>() {
            @Override
            public void onResponse(@NonNull Call<AllDepartmentModel> call, @NonNull Response<AllDepartmentModel> response) {

                try {
                    allDepartmentModel = response.body();
                    adapter = new GetDepartmentAD(DoctorListActivity.this, allDepartmentModel.data);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AllDepartmentModel> call, @NonNull Throwable t) {

            }
        });

//        Call<AllDepartmentModel> getDigServices=apiInterface.allDepartment(Constant.AUTH);
//        getDigServices.enqueue(new Callback<AllDepartmentModel>() {
//            @Override
//            public void onResponse(@NonNull Call<AllDepartmentModel> call, @NonNull Response<AllDepartmentModel> response) {
//                allDepartmentModel=response.body();
//                adapter=new GetDepartmentAD(getApplicationContext(),allDepartmentModel.data);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<AllDepartmentModel> call, @NonNull Throwable t) {
//                call.cancel();
//            }
//        });
    }
    //DoctorList

    private void recyclardoc_view() {
        Call<AllDoctorList> getDigServices=apiInterface.allDoctorServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllDoctorList>() {
            @Override
            public void onResponse(@NonNull Call<AllDoctorList> call, @NonNull Response<AllDoctorList> response) {
                assert response.body() != null;
                allDoctorlistModel=response.body().getData();
                adapterDoctor=new DoctorListAD(getApplicationContext(),allDoctorlistModel,onDocListClick);
                recyclerViewDoctor.setAdapter(adapterDoctor);
                Log.e("getAllDia",String.valueOf(allDoctorlistModel));
            }

            @Override
            public void onFailure(@NonNull Call<AllDoctorList> call, @NonNull Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.toolbarBtn){
            finish();
        }
    }

    @Override
    public void sendData(int id) {
        Log.e("doctor id",id+" ");
    }
}
