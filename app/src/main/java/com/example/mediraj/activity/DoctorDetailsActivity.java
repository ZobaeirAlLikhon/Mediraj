package com.example.mediraj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;
import com.example.mediraj.adaptar.DoctorListAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.SingleDoctor;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ApiInterface apiInterface;
    TextView toolbarText;
    ImageView toolbarBtn;
    String docId;
    ShapeableImageView docImg;
    TextView docName,docDes,docSpe,year,docInfo,docPlace,docTime,docDay,docFee,noData;
    ScrollView scrollView;
    AppCompatButton bookBtn;
    SingleDoctor singleDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        Intent intent = getIntent();
        if (intent !=null){
            docId = getIntent().getStringExtra("docId");
            Log.e("doctor id",docId);
        }
        initView();

        if (ConnectionManager.connection(this)){
            noData.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            getSingleDoctorData();
        }else{
            scrollView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.internet_connect_msg, Toast.LENGTH_SHORT).show();

        }
    }

    private void getSingleDoctorData() {

        Call<SingleDoctor> call = apiInterface.getSingleDoctorInfo(Constant.AUTH,docId, DataManager.getInstance().getUserData(this).data.id);
        call.enqueue(new Callback<SingleDoctor>() {
            @Override
            public void onResponse(@NonNull Call<SingleDoctor> call, @NonNull Response<SingleDoctor> response) {

                singleDoctor = response.body();
                assert singleDoctor != null;
                if (singleDoctor.response==200){
                    noData.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    setDoctorData(singleDoctor.data);

                }else {
                    scrollView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(@NonNull Call<SingleDoctor> call, @NonNull Throwable t) {
                call.cancel();
            }
        });





    }

    private void setDoctorData(SingleDoctor.Data data) {

        Glide.with(this)
                .load(Constant.DOCTOR_AVATAR_URL+data.avatar)
                .into(docImg);
        docName.setText(data.fullName);
        docDes.setText(data.designation+", "+data.organization);
        docSpe.setText(data.specialty);
        if (data.experience==1){
            year.setText(data.experience+" Year");
        }else{
            year.setText(data.experience+" Years");
        }

        String doctorInfo = data.specialty+"\n"+data.degree+"\n"+data.designation+", "+data.organization;
        docInfo.setText(doctorInfo);
        docPlace.setText(data.chember);
        docTime.setText(data.scheduleStart+"-"+data.scheduleEnd);
        docDay.setText(data.scheduleDays);
        docFee.setText(getString(R.string.moneySymbol)+" "+data.visitingFees);

    }

    private void initView() {
        apiInterface = APiClient.getClient().create(ApiInterface.class);

        //toolbar
        toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText(R.string.doctor_detail);
        toolbarBtn = findViewById(R.id.toolbarBtn);

        //activity

        docImg = findViewById(R.id.doctorImg);
        docName = findViewById(R.id.docName);
        docDes = findViewById(R.id.docDes);
        docSpe = findViewById(R.id.docSpe);
        year = findViewById(R.id.yearTxt);
        docInfo = findViewById(R.id.docInfo);
        docPlace = findViewById(R.id.place);
        docTime = findViewById(R.id.docTime);
        docDay = findViewById(R.id.docDay);
        bookBtn = findViewById(R.id.bookBtn);
        docFee = findViewById(R.id.docFee);
        noData = findViewById(R.id.noData);
        scrollView = findViewById(R.id.scrollView);

        toolbarBtn.setOnClickListener(this);
        bookBtn.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id==R.id.toolbarBtn){
            finish();
        }else if (id==R.id.bookBtn){
            startActivity(new Intent(this,DoctorBookingActivity.class)
                                    .putExtra("docId",docId)
                                    .putExtra("docName",singleDoctor.data.fullName)
                                    .putExtra("docSpe",singleDoctor.data.specialty)
                                    .putExtra("docDes",singleDoctor.data.degree)
                                    .putExtra("place",singleDoctor.data.designation+", "+singleDoctor.data.organization));
        }

    }
}