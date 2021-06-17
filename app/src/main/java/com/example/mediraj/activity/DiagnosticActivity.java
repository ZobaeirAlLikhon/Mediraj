package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.DiagnosticServicesAdapter;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.DiagnosticService;
import com.example.mediraj.model.AllDiagnosticModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosticActivity extends AppCompatActivity implements View.OnClickListener, DiagnosticServicesAdapter.OnDiagnosticClick {
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    DiagnosticServicesAdapter adapter;
    ImageView ivBack;
    TextView toolBarTxt, noData;
    List<AllDiagnosticModel.Datum> dataList = new ArrayList<>();
    public static final String TAG = DiagnosticActivity.class.getName();
    DiagnosticServicesAdapter.OnDiagnosticClick onDiagnosticClick;
    AppDatabase db;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        db = AppDatabase.getDbInstance(this);
        onDiagnosticClick = this;
        initView();
        recyclerView();
    }

    private void initView() {
        ivBack = findViewById(R.id.toolbarBtn);
        toolBarTxt = findViewById(R.id.toolbarText);
        toolBarTxt.setText(getString(R.string.ds));

        noData = findViewById(R.id.noData);

        recyclerView = findViewById(R.id.recy_view_diagnostic);
        recyclerView.setLayoutManager(new LinearLayoutManager(DiagnosticActivity.this, LinearLayoutManager.VERTICAL, false));

        fab = findViewById(R.id.goToCart);

        ivBack.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    private void recyclerView() {
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Call<AllDiagnosticModel> getDigServices = apiInterface.allDiagonsticServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllDiagnosticModel>() {
            @Override
            public void onResponse(Call<AllDiagnosticModel> call, Response<AllDiagnosticModel> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    AllDiagnosticModel allDiagnosticModel = response.body();
                    dataList.clear();
                    if (allDiagnosticModel.getResponse() == 200) {
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        dataList.addAll(allDiagnosticModel.getData());
                        adapter = new DiagnosticServicesAdapter(getApplicationContext(), allDiagnosticModel.getData(),onDiagnosticClick);
                        recyclerView.setAdapter(adapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AllDiagnosticModel> call, Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
                Log.e(TAG + " error", t.toString());
            }
        });

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.toolbarBtn) {
            finish();
            overridePendingTransition(0, 0);
        }else if (id==R.id.goToCart){
            startActivity(new Intent(this,CartActivity.class).putExtra("index","1"));
            finish();
            overridePendingTransition(0,0);
        }

    }

    @Override
    public void sendOrDeleteData(int position, String opType) {

        if (opType !=null && opType.equalsIgnoreCase("add")){
            Log.e(TAG+" Data",position+" "+opType+" "+"Yes");
            DiagnosticService diagnosticService = new DiagnosticService();
            diagnosticService.setItem_id(dataList.get(position).getId());
            diagnosticService.setItem_title(dataList.get(position).getTitle());
            diagnosticService.setItem_unit(dataList.get(position).getUnit());
            diagnosticService.setItem_qty("1");
            diagnosticService.setItem_price(dataList.get(position).getPrice());
            diagnosticService.setItem_subtotal(dataList.get(position).getPrice());
            db.diagnosticServiceDao().insertInfo(diagnosticService);
            Toast.makeText(this,getString(R.string.addtocart),Toast.LENGTH_SHORT).show();

        }else if (opType!=null && opType.equalsIgnoreCase("delete")){
            Log.e(TAG+" Data",position+" "+opType+" "+"Delete");
            db.diagnosticServiceDao().deleteById(dataList.get(position).getId());
            Toast.makeText(this,getString(R.string.removecart),Toast.LENGTH_SHORT).show();
        }

    }
}