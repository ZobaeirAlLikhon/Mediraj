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
import com.example.mediraj.adaptar.DoctorListAdapter;
import com.example.mediraj.adaptar.GetDepartmentAD;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.AllDepartmentModel;
import com.example.mediraj.model.SingleDepartment;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity implements View.OnClickListener, GetDepartmentAD.DoctorInterface {

    ApiInterface apiInterface;
    RecyclerView recyclerView,recyclerViewDoctor;
    AllDepartmentModel allDepartmentModel;
    GetDepartmentAD adapter;
    DoctorListAdapter adapterDoctor;
    ImageView ivBack;
    TextView toolbarTxt,noData;
    GetDepartmentAD.DoctorInterface doctorInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        initView();

        if (ConnectionManager.connection(this)){
            recyclerView();
        }else{
            Toast.makeText(this, R.string.internet_connect_msg, Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        doctorInterface = this;
        ivBack = findViewById(R.id.toolbarBtn);
        toolbarTxt = findViewById(R.id.toolbarText);
        noData = findViewById(R.id.noData);
        toolbarTxt.setText(R.string.doctorlist);
        recyclerViewDoctor=findViewById(R.id.recy_view_doc_vertical);
        recyclerView=findViewById(R.id.recy_view_depart_horizontal);


        ivBack.setOnClickListener(this);
    }


    //DepartmentList
    private void recyclerView() {
        Call<AllDepartmentModel> call = apiInterface.allDepartment(Constant.AUTH);
        call.enqueue(new Callback<AllDepartmentModel>() {
            @Override
            public void onResponse(@NonNull Call<AllDepartmentModel> call, @NonNull Response<AllDepartmentModel> response) {

                try {
                    allDepartmentModel = response.body();
                    if (allDepartmentModel.response==200){
                        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        adapter = new GetDepartmentAD(DoctorListActivity.this, allDepartmentModel.data,doctorInterface);
                        recyclerView.setAdapter(adapter);
                        singleDeptDoctor(1);
                    }else {
                        Toast.makeText(DoctorListActivity.this, allDepartmentModel.message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AllDepartmentModel> call, @NonNull Throwable t) {
                call.cancel();
            }
        });

    }
    //department wise  Doctor list
    private void singleDeptDoctor(int id){
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        Call<SingleDepartment> call = apiInterface.getDoctorByDept(Constant.AUTH,id);
        call.enqueue(new Callback<SingleDepartment>() {
            @Override
            public void onResponse(@NonNull Call<SingleDepartment> call, @NonNull Response<SingleDepartment> response) {
                DataManager.getInstance().hideProgressMessage();

                try {
                    SingleDepartment singleDepartment = response.body();
                    assert singleDepartment != null;
                    if (singleDepartment.response==200){
                        if (singleDepartment.data.doctors ==null){
                            recyclerViewDoctor.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }else if (singleDepartment.data.doctors.size() !=0){
                            noData.setVisibility(View.GONE);
                            recyclerViewDoctor.setVisibility(View.VISIBLE);
                            recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this,LinearLayoutManager.VERTICAL,false));
                            adapterDoctor = new DoctorListAdapter(DoctorListActivity.this, singleDepartment.data.doctors);
                            recyclerViewDoctor.setAdapter(adapterDoctor);
                        }else {
                            recyclerViewDoctor.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }

                    }else{
                        recyclerViewDoctor.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SingleDepartment> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
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
    public void sendDeptId(int deptId) {
        singleDeptDoctor(deptId);
    }



}
