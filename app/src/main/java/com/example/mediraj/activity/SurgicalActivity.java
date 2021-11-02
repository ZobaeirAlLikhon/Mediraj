package com.example.mediraj.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.R;
import com.example.mediraj.adaptar.SurgicalAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.SurgicalService;
import com.example.mediraj.model.AllSurgicalModel;
import com.example.mediraj.model.ClinicalModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Hashtable;
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
    List<AllSurgicalModel.Datum> dataList = new ArrayList<>();
    AppDatabase db;
    TextView noData;
    ImageView ivBack;
    TextView itemCount;
    FloatingActionButton fab;
    FrameLayout frameLayout;
    int count = 0;
    Hashtable<Integer, Long> storedIds = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgical);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        db = AppDatabase.getDbInstance(this);
        onSurgicalClick = this;


        initView();

        if (ConnectionManager.connection(this)) {
            recyclerView();
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        fab = findViewById(R.id.goToCart);
        noData = findViewById(R.id.noData);
        ivBack = findViewById(R.id.toolbarBtn);
//        toolBarTxt = findViewById(R.id.toolbarText);
//        toolBarTxt.setText(getString(R.string.md));
        itemCount = findViewById(R.id.itemCount);
        frameLayout = findViewById(R.id.frameLay);
        recyclerView = findViewById(R.id.recy_view_surgical);
        recyclerView.setLayoutManager(new LinearLayoutManager(SurgicalActivity.this, LinearLayoutManager.VERTICAL, false));


        ivBack.setOnClickListener(this);
        fab.setOnClickListener(this);

        frameLayout.setVisibility(View.GONE);

        EditText searchBox = findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });


    }

    private void filterList(String text) {
        List<AllSurgicalModel.Datum> filteredList = new ArrayList<>();
        Log.e("data list 1",dataList.toString()+"\n"+dataList.size());
        for (AllSurgicalModel.Datum item:dataList){
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
                Log.e("filter item",filteredList.toString());
            }
        }

        adapter.searchList(filteredList);
    }

    private void recyclerView() {
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Call<AllSurgicalModel> getDigServices = apiInterface.allSurgicalServices(Constant.AUTH);
        getDigServices.enqueue(new Callback<AllSurgicalModel>() {

            @Override
            public void onResponse(@NonNull Call<AllSurgicalModel> call, @NonNull Response<AllSurgicalModel> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    dataList.clear();
                    if (response.body().response == 200) {
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        allSurgicalModel = response.body().data;
                        dataList.addAll(response.body().data);
                        adapter = new SurgicalAdapter(getApplicationContext(), allSurgicalModel, onSurgicalClick);
                        recyclerView.setAdapter(adapter);
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }
                } catch (Exception e) {
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
        long insertionId;
        if (opType != null && opType.equalsIgnoreCase("add")) {
            SurgicalService surgicalService = new SurgicalService();
            surgicalService.setItem_id(dataList.get(position).getId());
            surgicalService.setItem_title(dataList.get(position).getTitle());
            surgicalService.setItem_unit(dataList.get(position).getUnit());
            surgicalService.setItem_qty("1");
            surgicalService.setItem_price(dataList.get(position).getPrice());
            surgicalService.setItem_subtotal(dataList.get(position).getPrice());
            insertionId = db.surgicalServiceDao().insertInfo(surgicalService);
            storedIds.put(dataList.get(position).getId(), insertionId);
            Toast.makeText(this, getString(R.string.addtocart), Toast.LENGTH_SHORT).show();
            count += 1;

        } else if (opType != null && opType.equalsIgnoreCase("delete")) {
            long id = storedIds.get(dataList.get(position).getId());
            db.surgicalServiceDao().deleteByIdOne(id);
            storedIds.remove(dataList.get(position).getId());
            count -= 1;
            Toast.makeText(this, getString(R.string.removecart), Toast.LENGTH_SHORT).show();
        }


        if (count == 0) {
            frameLayout.setVisibility(View.GONE);
            itemCount.setVisibility(View.GONE);
        } else {
            frameLayout.setVisibility(View.VISIBLE);
            itemCount.setVisibility(View.VISIBLE);
            itemCount.setText(count + "");
        }


    }

    @Override
    public void sendUrl(String imageName) {

        showImageDialog(imageName);

    }

    private void showImageDialog(String imageName) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Widget_Material_ListPopupWindow;
        dialog.setContentView(R.layout.image_show_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        ImageView test = dialog.findViewById(R.id.tapImage);
        TextView test1 = dialog.findViewById(R.id.tapText);

        if (imageName.equals("test")){
            test.setVisibility(View.GONE);
            test1.setVisibility(View.VISIBLE);

        }else{
            test1.setVisibility(View.GONE);
            test.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .load(Constant.SURGICAL_AVATAR_URL+imageName)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_surgical1))
                    .into(test);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.goToCart) {
            startActivity(new Intent(this, CartActivity.class).putExtra("index", "3"));
            finish();

        } else if (id == R.id.toolbarBtn) {
            finish();

        }

    }
}