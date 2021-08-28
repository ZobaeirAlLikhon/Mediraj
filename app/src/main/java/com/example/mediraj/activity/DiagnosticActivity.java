package com.example.mediraj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.DiagnosticServicesAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.DiagnosticService;
import com.example.mediraj.model.AllDiagnosticModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosticActivity extends AppCompatActivity implements View.OnClickListener, DiagnosticServicesAdapter.OnDiagnosticClick {
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    DiagnosticServicesAdapter adapter;
    ImageView ivBack;
    TextView toolBarTxt, noData,itemCount;
    List<AllDiagnosticModel.Datum> dataList = new ArrayList<>();
    public static final String TAG = DiagnosticActivity.class.getName();
    DiagnosticServicesAdapter.OnDiagnosticClick onDiagnosticClick;
    AppDatabase db;
    FloatingActionButton fab;
    FrameLayout frameLayout;
    int count = 0;
    Hashtable<Integer,Long> storedIds = new Hashtable<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        db = AppDatabase.getDbInstance(this);
        onDiagnosticClick = this;

        initView();

        if (ConnectionManager.connection(this)) {
            recyclerView();
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }


    }

    private void initView() {
        ivBack = findViewById(R.id.toolbarBtn);
        toolBarTxt = findViewById(R.id.toolbarText);
        toolBarTxt.setText(getString(R.string.ds));
        noData = findViewById(R.id.noData);
        recyclerView = findViewById(R.id.recy_view_diagnostic);
        fab = findViewById(R.id.goToCart);
        itemCount = findViewById(R.id.itemCount);
        frameLayout = findViewById(R.id.frameLay);
        ivBack.setOnClickListener(this);
        fab.setOnClickListener(this);

        frameLayout.setVisibility(View.GONE);

    }

    private void recyclerView() {
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Call<AllDiagnosticModel> getDigServices = apiInterface.allDiagonsticServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllDiagnosticModel>() {
            @Override
            public void onResponse(@NotNull Call<AllDiagnosticModel> call, @NotNull Response<AllDiagnosticModel> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    AllDiagnosticModel allDiagnosticModel = response.body();
                    dataList.clear();
                    assert allDiagnosticModel != null;
                    if (allDiagnosticModel.getResponse() == 200) {
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        dataList.addAll(allDiagnosticModel.getData());
                        recyclerView.setLayoutManager(new LinearLayoutManager(DiagnosticActivity.this, LinearLayoutManager.VERTICAL, false));
                        adapter = new DiagnosticServicesAdapter(getApplicationContext(), allDiagnosticModel.getData(), onDiagnosticClick);
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
            public void onFailure(@NotNull Call<AllDiagnosticModel> call, @NotNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.toolbarBtn) {
            finish();
        } else if (id == R.id.goToCart) {
            startActivity(new Intent(this, CartActivity.class).putExtra("index", "1"));
            finish();
        }

    }

    @Override
    public void sendOrDeleteData(int position, String opType) {
            long insertionId;
        if (opType != null && opType.equalsIgnoreCase("add")) {
            DiagnosticService diagnosticService = new DiagnosticService();
            diagnosticService.setItem_id(dataList.get(position).getId());
            diagnosticService.setItem_title(dataList.get(position).getTitle());
            diagnosticService.setItem_unit(dataList.get(position).getUnit());
            diagnosticService.setItem_qty("1");
            diagnosticService.setItem_price(dataList.get(position).getPrice());
            diagnosticService.setItem_subtotal(dataList.get(position).getPrice());
            insertionId =  db.diagnosticServiceDao().insertInfo(diagnosticService);
            storedIds.put(dataList.get(position).getId(),insertionId);
            Toast.makeText(this, getString(R.string.addtocart), Toast.LENGTH_SHORT).show();
            count +=1;
        } else if (opType != null && opType.equalsIgnoreCase("delete")) {
            long id = storedIds.get(dataList.get(position).getId());
            db.diagnosticServiceDao().deleteByIdOne(id);
            storedIds.remove(dataList.get(position).getId());
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