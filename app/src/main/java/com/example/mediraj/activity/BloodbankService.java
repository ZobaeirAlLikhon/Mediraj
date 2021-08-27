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
import com.example.mediraj.adaptar.BloodAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.AllbloodModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodbankService extends AppCompatActivity implements View.OnClickListener {

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    AllbloodModel allBloodModel;
    BloodAdapter adapter;
    TextView toolbarText,noData;
    ImageView toolbarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank_service);

        initView();
        apiInterface = APiClient.getClient().create(ApiInterface.class);

        if (ConnectionManager.connection(this)){
            recyclerView();
        }else{
            Toast.makeText(this,R.string.internet_connect_msg,Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        toolbarText = findViewById(R.id.toolbarText);
        toolbarBtn = findViewById(R.id.toolbarBtn);
        noData = findViewById(R.id.noData);
        toolbarText.setText(R.string.bb);
        recyclerView=findViewById(R.id.recy_view_blood);
        recyclerView.setLayoutManager(new LinearLayoutManager(BloodbankService.this,LinearLayoutManager.VERTICAL,false));

        toolbarBtn.setOnClickListener(this);
    }

    private void recyclerView() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        Call<AllbloodModel> getDigServices=apiInterface.allBloodServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllbloodModel>() {
            @Override
            public void onResponse(@NonNull Call<AllbloodModel> call, @NonNull Response<AllbloodModel> response) {
                DataManager.getInstance().hideProgressMessage();


                try {
                    allBloodModel =response.body();
                    assert allBloodModel != null;
                    if (allBloodModel.getResponse()==200){
                        recyclerView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                        adapter=new BloodAdapter(getApplicationContext(), allBloodModel.getData());
                        recyclerView.setAdapter(adapter);
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("getAllDia",String.valueOf(allBloodModel));
            }

            @Override
            public void onFailure(@NonNull Call<AllbloodModel> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.toolbarBtn){
            finish();
        }
    }
}
