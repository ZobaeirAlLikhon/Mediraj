package com.example.mediraj.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.DiagnosticService;
import com.example.mediraj.model.CheckoutResModel;
import com.example.mediraj.model.checkout.Checkout;
import com.example.mediraj.model.checkout.CheckoutModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutInformation extends AppCompatActivity implements View.OnClickListener {

    TextView toolbarTxt;
    ImageView ivBack;
    TextView userName, orderItem, orderTotalPrice, summarySubtotal, summaryDeliveryPrice, summaryTotalPrice;
    EditText userAddress;
    MaskEditText userMobile;
    ImageView btn_img;
    AppDatabase db;
    ApiInterface apiInterface;
    String databaseRef = null;
    int price = 0;
    CardView checkoutConfirmBtn;
    List<DiagnosticService> oldDataList = new ArrayList<>();
    List<Checkout> newDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_information);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        db = AppDatabase.getDbInstance(this);


        try {
            if (getIntent() != null) {
                databaseRef = getIntent().getStringExtra("data") != null ? getIntent().getStringExtra("data") : "0";
                Log.e("dataRef", databaseRef + " ");

                if (databaseRef.equals("1")) {
                    oldDataList = db.diagnosticServiceDao().getAll();
                    newDataList = db.diagnosticServiceDao().getCheckoutData();

                    if (oldDataList.size() != 0) {
                        for (int i = 0; i < oldDataList.size(); i++) {
                            price = price + oldDataList.get(i).getItem_subtotal();
                        }
                        Log.e("price", price + " * " + oldDataList.size() +" * "+newDataList.size());
                    }

                }else if (databaseRef.equals("2")){

                }else if (databaseRef.equals("3")){

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
    }

    private void initView() {
        toolbarTxt = findViewById(R.id.toolbarText);
        ivBack = findViewById(R.id.toolbarBtn);
        toolbarTxt.setText("Order");

        userName = findViewById(R.id.check_user_name);
        userAddress = findViewById(R.id.Tv_checkupname);
        userMobile = findViewById(R.id.userCell);
        btn_img = findViewById(R.id.btn_img);


        //OrderItem,orderTotalPrice,summarySubtotal,summaryDeliveryPrice,summaryTotalPrice

        orderItem = findViewById(R.id.Order_item);
        orderTotalPrice = findViewById(R.id.order_tottal_price);
        summarySubtotal = findViewById(R.id.summary_subtotal_tv);
        summaryDeliveryPrice = findViewById(R.id.summary_delivery_price);
        summaryTotalPrice = findViewById(R.id.summary_total_price);
        checkoutConfirmBtn = findViewById(R.id.checkout_confirm_btn);

        ivBack.setOnClickListener(this);
        btn_img.setOnClickListener(this);
        checkoutConfirmBtn.setOnClickListener(this);

        setUserData();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.toolbarBtn) {
            finish();
        } else if (id == R.id.btn_img) {
            userMobile.setEnabled(true);
        }else if (id==R.id.checkout_confirm_btn){
            if (ConnectionManager.connection(this)){
               // validateData();
                sendCheckoutData();
            }else {
                Toast.makeText(this,getString(R.string.internet_connect_msg),Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void validateData() {
        if (userMobile.getText().toString().isEmpty()){
            userMobile.setError(getString(R.string.userPhone_error));
            userMobile.requestFocus();
        }
    }

    private void sendCheckoutData() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setUser(Integer.parseInt(DataManager.getInstance().getUserData(getApplicationContext()).data.id));
        checkoutModel.setName(DataManager.getInstance().getUserData(getApplicationContext()).data.name);
        checkoutModel.setMobile(userMobile.getText().toString());
        checkoutModel.setAddress(DataManager.getInstance().getUserData(getApplicationContext()).data.address);
        checkoutModel.setCheckouts(newDataList);

        Call<CheckoutResModel> call = apiInterface.sendCheckoutProductData(Constant.AUTH,checkoutModel);
        call.enqueue(new Callback<CheckoutResModel>() {
            @Override
            public void onResponse(@NonNull Call<CheckoutResModel> call, @NonNull Response<CheckoutResModel> response) {
                try {
                    DataManager.getInstance().hideProgressMessage();
                    CheckoutResModel checkoutResModel = response.body();
                    Toast.makeText(getApplicationContext(),checkoutResModel.message,Toast.LENGTH_LONG).show();
                    //delete data from roomdb
                    db.diagnosticServiceDao().deleteAllData();
                    //go order history page
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckoutResModel> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();

            }
        });







    }

    private void setUserData() {
        if (DataManager.getInstance().getUserData(this).data != null) {
            if (DataManager.getInstance().getUserData(this).data.name != null) {
                userName.setText(DataManager.getInstance().getUserData(this).data.name);
            }
            if (DataManager.getInstance().getUserData(this).data.address != null) {
                userAddress.setText(DataManager.getInstance().getUserData(this).data.address);
            }
            if (DataManager.getInstance().getUserData(this).data.mobile != null) {
                String phoneNumber = "+88 " + DataManager.getInstance().getUserData(this).data.mobile.substring(0, 5) + "-" + DataManager.getInstance().getUserData(this).data.mobile.substring(5);
                userMobile.setText(phoneNumber);
            }

            if (oldDataList.size()==1){
                orderItem.setText(oldDataList.get(0).getItem_title());
                orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                summaryDeliveryPrice.setText("20");
                summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
            }else if (oldDataList.size() > 1){
                orderItem.setText("Multiple item");
                orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                summaryDeliveryPrice.setText(getString(R.string.moneySymbol)+" 20");
                summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
            }
        }

    }
}