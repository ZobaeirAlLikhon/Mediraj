package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.Get_SurgicalAD;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.PathologyServices;
import com.example.mediraj.localdb.SurgicalService;
import com.example.mediraj.model.AllPathologyModel;
import com.example.mediraj.model.AllSurgicalModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurgicalActivity extends AppCompatActivity implements Get_SurgicalAD.OnSurgicalClick, View.OnClickListener {
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllSurgicalModel.Datum> allSurgicalModel;
    Get_SurgicalAD adapter;
    Get_SurgicalAD.OnSurgicalClick onSurgicalClick;
    List<AllSurgicalModel.Datum> datalist=new ArrayList<>();
    AppDatabase db;
    FloatingActionButton floatBT;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgical);
        onSurgicalClick=this;
        db = AppDatabase.getDbInstance(this);
        floatBT=findViewById(R.id.goToCart);
        noData=findViewById(R.id.noData);
        floatBT.setOnClickListener(this);

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
                try {
                    datalist.clear();
                    if (response.body().getResponse() == 200) {
                        noData.setVisibility(View.GONE);
                        floatBT.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        allSurgicalModel = response.body().getData();
                        datalist.addAll(response.body().getData());
                        adapter = new Get_SurgicalAD(getApplicationContext(), allSurgicalModel, onSurgicalClick);
                        recyclerView.setAdapter(adapter);
                        Log.e("getAllDia..", allSurgicalModel.get(0).getTitle());
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        floatBT.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<AllSurgicalModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void sendOrDeleteData_surgical(int position, String opType) {
        if (opType != null && opType.equalsIgnoreCase("add")) {
            Log.e("TAG" + " Data", position + " " + opType + " " + "Yes");
            SurgicalService surgicalService = new SurgicalService();
            surgicalService.setItem_id(datalist.get(position).getId());
            surgicalService.setItem_title(datalist.get(position).getTitle());
            surgicalService.setItem_unit(datalist.get(position).getUnit());
            surgicalService.setItem_qty("1");
            surgicalService.setItem_price(datalist.get(position).getPrice());
            surgicalService.setItem_subtotal(datalist.get(position).getPrice());
            db.surgicalServiceDao().insertInfo(surgicalService);
            Toast.makeText(this, getString(R.string.addtocart), Toast.LENGTH_SHORT).show();

        } else if (opType != null && opType.equalsIgnoreCase("delete")) {
            Log.e("TAG" + " Data", position + " " + opType + " " + "Delete");
            db.surgicalServiceDao().deleteById_Surgical(datalist.get(position).getId());
            Toast.makeText(this, getString(R.string.removecart), Toast.LENGTH_SHORT).show();
        }
        

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.goToCart)
        {
            startActivity(new Intent(this, CartActivity.class).putExtra("index", "3"));
            finish();
            overridePendingTransition(0, 0);
        }

    }
}