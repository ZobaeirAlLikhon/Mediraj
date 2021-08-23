package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.mediraj.adaptar.HomePathologyAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.localdb.AppDatabase;
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
    List<AllPathologyModel.Datum> dataList=new ArrayList<>();
    AppDatabase db;
    TextView noData;

    FloatingActionButton fab;
    FrameLayout frameLayout;
    int count = 0;
    ImageView ivBack;
    TextView toolBarTxt,itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pathology);
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        db = AppDatabase.getDbInstance(this);
        onPathologyClickInterface=this;


        initView();

        if (ConnectionManager.connection(this)){
            recyclerView();
        }else{
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        fab=findViewById(R.id.goToCart);
        noData=findViewById(R.id.noData);
        ivBack = findViewById(R.id.toolbarBtn);
        toolBarTxt = findViewById(R.id.toolbarText);
        toolBarTxt.setText(getString(R.string.hp));
        itemCount = findViewById(R.id.itemCount);
        frameLayout = findViewById(R.id.frameLay);
        recyclerView=findViewById(R.id.recy_view_pathology);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomePathologyActivity.this,LinearLayoutManager.VERTICAL,false));

        ivBack.setOnClickListener(this);
        fab.setOnClickListener(this);


        frameLayout.setVisibility(View.GONE);
    }


    private void recyclerView() {
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Call<AllPathologyModel> getDigServices=apiInterface.allPathologyServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllPathologyModel>() {
            @Override
            public void onResponse(@NonNull Call<AllPathologyModel> call, @NonNull Response<AllPathologyModel> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    dataList.clear();
                    assert response.body() != null;
                    if (response.body().getResponse()==200) {
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        allPathologyModel = response.body().getData();
                        dataList.addAll(response.body().getData());
                        adapter = new HomePathologyAdapter(getApplicationContext(), allPathologyModel, onPathologyClickInterface);
                        recyclerView.setAdapter(adapter);
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }catch (Exception e){e.printStackTrace();}

            }

            @Override
            public void onFailure(@NonNull Call<AllPathologyModel> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
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
        }else if (id==R.id.toolbarBtn){
            finish();
            overridePendingTransition(0, 0);
        }

    }

    @Override
    public void sendOrDelete(int position, String opType) {
        if (opType != null && opType.equalsIgnoreCase("add")) {
            PathologyServices pathologyServices = new PathologyServices();
            pathologyServices.setItem_id(dataList.get(position).getId());
            pathologyServices.setItem_title(dataList.get(position).getTitle());
            pathologyServices.setItem_unit(dataList.get(position).getUnit());
            pathologyServices.setItem_qty("1");
            pathologyServices.setItem_price(dataList.get(position).getPrice());
            pathologyServices.setItem_subtotal(dataList.get(position).getPrice());
            db.pathologyServicesDao().insertInfo(pathologyServices);
            Toast.makeText(this, getString(R.string.addtocart), Toast.LENGTH_SHORT).show();
            count +=1;

        } else if (opType != null && opType.equalsIgnoreCase("delete")) {
            db.pathologyServicesDao().deleteByIdPath(dataList.get(position).getId());
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
}