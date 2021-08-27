package com.example.mediraj.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.BloodBooking_Model;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodBookingctivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText blname, bladdress, blgroup;
    MaskEditText contact;
    String nameST,contractST,addressST,user_ID,group_ID;
    CardView btnBloodReq;
    ApiInterface apiInterface;
    TextView toolbarText;
    ImageView toolbarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bookingctivity);

        apiInterface= APiClient.getClient().create(ApiInterface.class);

        try {
            Intent intent = getIntent();
            if (intent !=null) {
                group_ID = getIntent().getStringExtra("groupID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        init();

    }

    private void validation() {




        if (blname.getText().toString().isEmpty()) {
            blname.setError("Enter Your Name");
            blname.requestFocus();
        }
        else if (contact.getText().toString().isEmpty()) {
            contact.setError("Enter Your Mobile Number");
            contact.requestFocus();
        }else if (contact.getText().toString().length() !=16){
            contact.setError("Enter Your Mobile Number");
            contact.requestFocus();
        }
        else if (bladdress.getText().toString().isEmpty()) {
            bladdress.setError("Enter Your Address");
            bladdress.requestFocus();
        }
        else{
            sendData();
        }

    }

    private void sendData() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        Map<String, String> map = new HashMap<>();
        map.put("group_id",group_ID);
        map.put("user_id",user_ID);
        map.put("name",blname.getText().toString());
        map.put("mobile",contact.getText().toString());
        map.put("address",bladdress.getText().toString());
        Call<BloodBooking_Model> call=apiInterface.blood_booking(Constant.AUTH,map);
        call.enqueue(new Callback<BloodBooking_Model>() {
            @Override
            public void onResponse(@NonNull Call<BloodBooking_Model> call, @NonNull Response<BloodBooking_Model> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    BloodBooking_Model bloodBooking_model = response.body();
                    assert bloodBooking_model != null;
                    if (bloodBooking_model.getResponse()==200){
                        Toast.makeText(getApplicationContext(),bloodBooking_model.getMessage(),Toast.LENGTH_SHORT).show();
                        confirmAlert(BloodBookingctivity.this);
                    }else{
                        Toast.makeText(getApplicationContext(),bloodBooking_model.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BloodBooking_Model> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
            }
        });
    }

    private void init() {
        toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText(R.string.br);
        toolbarBtn = findViewById(R.id.toolbarBtn);
        blname = findViewById(R.id.blood_usser_name);
        bladdress = findViewById(R.id.blood_usser_address);
        contact = findViewById(R.id.blood_usser_contact);
        btnBloodReq=findViewById(R.id.confirm_btn);
        setUserData();

        toolbarBtn.setOnClickListener(this);
        btnBloodReq.setOnClickListener(this);
    }

    private void setUserData() {
        try{
        user_ID = DataManager.getInstance().getUserData(this).data.id;
        nameST = DataManager.getInstance().getUserData(this).data.name;
        contractST = DataManager.getInstance().getUserData(this).data.mobile;
        if (DataManager.getInstance().getUserData(this).data.address!=null){
            addressST = DataManager.getInstance().getUserData(this).data.address;
            bladdress.setText(addressST);
        }
        blname.setText(nameST);
        contact.setText("+88 "+contractST.substring(0,5)+"-"+contractST.substring(5));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        int id =v.getId();
        if (id==R.id.toolbarBtn){
            finish();
        }else if (id==R.id.confirm_btn){
            if (ConnectionManager.connection(this)){
                validation();
            }else {
                Toast.makeText(this, R.string.internet_connect_msg, Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void confirmAlert(Activity activity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Widget_Material_ListPopupWindow;
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.order_confirm_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        AppCompatButton homeBtn = dialog.findViewById(R.id.backtoHome);
        TextView orderNo = dialog.findViewById(R.id.orderNo);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
                activity.startActivity(new Intent(activity.getApplicationContext(),HomeActivity.class));
                activity.finish();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}