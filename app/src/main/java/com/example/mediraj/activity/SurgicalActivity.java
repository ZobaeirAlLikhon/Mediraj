package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.SurgicalAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.SurgicalService;
import com.example.mediraj.model.AllSurgicalModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurgicalActivity extends AppCompatActivity implements SurgicalAdapter.OnSurgicalClick, View.OnClickListener {
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllSurgicalModel.Datum> allSurgicalModel;
    SurgicalAdapter adapter;
    SurgicalAdapter.OnSurgicalClick onSurgicalClick;
    List<AllSurgicalModel.Datum> dataList=new ArrayList<>();
    AppDatabase db;
    TextView noData;

    ImageView ivBack;
    TextView toolBarTxt,itemCount;
    FloatingActionButton fab;
    FrameLayout frameLayout;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgical);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        db = AppDatabase.getDbInstance(this);
        onSurgicalClick=this;


        initView();

        if (ConnectionManager.connection(this)) {
            recyclerView();
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        fab=findViewById(R.id.goToCart);
        noData=findViewById(R.id.noData);
        ivBack = findViewById(R.id.toolbarBtn);
        toolBarTxt = findViewById(R.id.toolbarText);
        toolBarTxt.setText(getString(R.string.md));
        itemCount = findViewById(R.id.itemCount);
        frameLayout = findViewById(R.id.frameLay);
        recyclerView=findViewById(R.id.recy_view_surgical);
        recyclerView.setLayoutManager(new LinearLayoutManager(SurgicalActivity.this,LinearLayoutManager.VERTICAL,false));


        ivBack.setOnClickListener(this);
        fab.setOnClickListener(this);

        frameLayout.setVisibility(View.GONE);



    }

    private void recyclerView() {
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Call<AllSurgicalModel> getDigServices=apiInterface.allSurgicalServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllSurgicalModel>(){

            @Override
            public void onResponse(@NonNull Call<AllSurgicalModel> call, @NonNull Response<AllSurgicalModel> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    dataList.clear();
                    assert response.body() != null;
                    if (response.body().response == 200) {
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        allSurgicalModel = response.body().data;
                        dataList.addAll(response.body().data);
                        adapter = new SurgicalAdapter(getApplicationContext(), allSurgicalModel, onSurgicalClick);
                        recyclerView.setAdapter(adapter);
                        Log.e("getAllDia..", allSurgicalModel.get(0).getTitle());
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(@NonNull Call<AllSurgicalModel> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
            }
        });
    }

    @Override
    public void sendOrDeleteData_surgical(int position, String opType) {
        if (opType != null && opType.equalsIgnoreCase("add")) {
            SurgicalService surgicalService = new SurgicalService();
            surgicalService.setItem_id(dataList.get(position).getId());
            surgicalService.setItem_title(dataList.get(position).getTitle());
            surgicalService.setItem_unit(dataList.get(position).getUnit());
            surgicalService.setItem_qty("1");
            surgicalService.setItem_price(dataList.get(position).getPrice());
            surgicalService.setItem_subtotal(dataList.get(position).getPrice());
            db.surgicalServiceDao().insertInfo(surgicalService);
            Toast.makeText(this, getString(R.string.addtocart), Toast.LENGTH_SHORT).show();
            count +=1;

        } else if (opType != null && opType.equalsIgnoreCase("delete")) {
            db.surgicalServiceDao().deleteById_Surgical(dataList.get(position).getId());
            count -=1;
            Toast.makeText(this, getString(R.string.removecart), Toast.LENGTH_SHORT).show();
        }



        if (count==0){
            frameLayout.setVisibility(View.GONE);
            itemCount.setVisibility(View.GONE);
        }else {
            frameLayout.setVisibility(View.VISIBLE);
            itemCount.setVisibility(View.VISIBLE);
            itemCount.setText(count+"");
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
        }else if (id==R.id.toolbarBtn){
            finish();
            overridePendingTransition(0, 0);
        }

    }
}