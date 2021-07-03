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
import com.example.mediraj.adaptar.HomePathologyAdapter;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.DiagnosticService;
import com.example.mediraj.localdb.PathologyServices;
import com.example.mediraj.model.AllPathologyModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePathologyActivity extends AppCompatActivity implements View.OnClickListener, HomePathologyAdapter.OnPathologyClickInterface {

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<AllPathologyModel.Datum> allPathologyModel;
    HomePathologyAdapter adapter;
    HomePathologyAdapter.OnPathologyClickInterface onPathologyClickInterface;
    List<AllPathologyModel.Datum> datalist=new ArrayList<>();
    AppDatabase db;
    FloatingActionButton floatingActionButton;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pathology);
        floatingActionButton=findViewById(R.id.goToCart);
        noData=findViewById(R.id.noData);
        onPathologyClickInterface=this;
        floatingActionButton.setOnClickListener(this);

        db = AppDatabase.getDbInstance(this);
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
                try {
                    datalist.clear();
                    if (response.body().getResponse()==200) {
                        noData.setVisibility(View.GONE);
                        floatingActionButton.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        allPathologyModel = response.body().getData();
                        datalist.addAll(response.body().getData());
                        adapter = new HomePathologyAdapter(getApplicationContext(), allPathologyModel, onPathologyClickInterface);
                        recyclerView.setAdapter(adapter);
                        Log.e("getAllDia", String.valueOf(allPathologyModel));
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        floatingActionButton.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }catch (Exception e){e.printStackTrace();}

            }

            @Override
            public void onFailure(Call<AllPathologyModel> call, Throwable t) {

            }
        });
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.goToCart)
        {

            startActivity(new Intent(this, CartActivity.class).putExtra("index", "2"));
            finish();
            overridePendingTransition(0, 0);
        }

    }

    @Override
    public void sendOrDelete(int position, String opType) {
        if (opType != null && opType.equalsIgnoreCase("add")) {
            Log.e("TAG" + " Data", position + " " + opType + " " + "Yes");
            PathologyServices pathologyServices = new PathologyServices();
            pathologyServices.setItem_id(datalist.get(position).getId());
            pathologyServices.setItem_title(datalist.get(position).getTitle());
            pathologyServices.setItem_unit(datalist.get(position).getUnit());
            pathologyServices.setItem_qty("1");
            pathologyServices.setItem_price(datalist.get(position).getPrice());
            pathologyServices.setItem_subtotal(datalist.get(position).getPrice());
            db.pathologyServicesDao().insertInfo(pathologyServices);
            Toast.makeText(this, getString(R.string.addtocart), Toast.LENGTH_SHORT).show();

        } else if (opType != null && opType.equalsIgnoreCase("delete")) {
            Log.e("TAG" + " Data", position + " " + opType + " " + "Delete");
            db.pathologyServicesDao().deleteByIdPath(datalist.get(position).getId());
            Toast.makeText(this, getString(R.string.removecart), Toast.LENGTH_SHORT).show();
        }

    }
}