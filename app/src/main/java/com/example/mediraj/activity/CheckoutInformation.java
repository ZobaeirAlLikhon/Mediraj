package com.example.mediraj.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.DiagnosticService;
import com.example.mediraj.localdb.PathologyServices;
import com.example.mediraj.localdb.SurgicalService;
import com.example.mediraj.model.ProductConfirmation;
import com.example.mediraj.model.checkout.Checkout;
import com.example.mediraj.model.checkout.CheckoutModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;
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
    AppCompatButton checkoutConfirmBtn;
    List<Checkout> newDataList = new ArrayList<>();
    List<DiagnosticService> diagnosticServiceList = new ArrayList<>();
    List<PathologyServices> pathologyServicesList = new ArrayList<>();
    List<SurgicalService> surgicalServiceList = new ArrayList<>();

    //list for store id's
    List<Long> productId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_information);

        apiInterface = APiClient.getClient().create(ApiInterface.class);
        db = AppDatabase.getDbInstance(this);


        try {
            if (getIntent() != null) {
                databaseRef = getIntent().getStringExtra("data");
                Log.e("dataRef", databaseRef + " ");

                switch (databaseRef) {
                    case "1":
                        newDataList = db.diagnosticServiceDao().getCheckoutData();
                        diagnosticServiceList = db.diagnosticServiceDao().getAll();

                        for(int i =0 ; i < diagnosticServiceList.size() ; i++){
                            productId.add((long) diagnosticServiceList.get(i).getId());
                        }
                        break;
                    case "2":
                        newDataList = db.pathologyServicesDao().getCheckoutDataPath();
                        pathologyServicesList = db.pathologyServicesDao().getAllPathology();
                        for(int i =0 ; i < pathologyServicesList.size() ; i++){
                            productId.add((long) pathologyServicesList.get(i).getId());
                        }
                        break;
                    case "3":
                        newDataList = db.surgicalServiceDao().getCheckoutData_surgical();
                        surgicalServiceList = db.surgicalServiceDao().getAllSurgical();
                        for(int i =0 ; i < surgicalServiceList.size() ; i++){
                            productId.add((long) surgicalServiceList.get(i).getId());
                        }
                        break;
                }

                if (newDataList.size() != 0) {
                    for (int i = 0; i < newDataList.size(); i++) {
                        price = price + newDataList.get(i).item_subtotal;
                    }
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
        toolbarTxt.setText(R.string.order);

        userName = findViewById(R.id.check_user_name);
        userAddress = findViewById(R.id.userAddress);
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
            overridePendingTransition(0,0);
            finish();
        } else if (id == R.id.btn_img) {
            if (userMobile.isEnabled()){
                userMobile.setEnabled(false);
                String phoneNumber = "+88 " + DataManager.getInstance().getUserData(this).data.mobile.substring(0, 5) + "-" + DataManager.getInstance().getUserData(this).data.mobile.substring(5);
                userMobile.setText(phoneNumber);
            }else {
                userMobile.setEnabled(true);
                userMobile.setText("");
            }
        }else if (id==R.id.checkout_confirm_btn){
            if (ConnectionManager.connection(this)){
                validateData();
            }else {
                Toast.makeText(this,getString(R.string.internet_connect_msg),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateData() {

        String mobileNumber;

        //mobile number format
        try {
            if (userMobile.getText().toString().length()>9){

                String cell = userMobile.getText().toString().split(" ")[1];
                mobileNumber = cell.split("-")[0]+cell.split("-")[1];

                if (userMobile.getText().toString().isEmpty()){
                    userMobile.setError(getString(R.string.userPhone_error));
                    userMobile.requestFocus();
                }else if (userMobile.getText().toString().length() < 16 && userMobile.getText().toString().length()>16){
                    userMobile.setError(getString(R.string.userPhone_error_valid));
                    userMobile.requestFocus();
                }
                else if (mobileNumber.length() != 11){
                    userMobile.setError(getString(R.string.userPhone_error_valid));
                    userMobile.requestFocus();
                }else if (!mobileNumber.startsWith("0")){
                    userMobile.setError(getString(R.string.userPhone_error_number));
                    userMobile.requestFocus();
                } else if (userAddress.getText().toString().isEmpty()){
                    userAddress.setError(getString(R.string.please_enter_your_address));
                    userAddress.requestFocus();
                }else{
                      alertLogout();
                }
            }else {
                userMobile.setError(getString(R.string.userPhone_error_valid));
                userMobile.requestFocus();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void sendCheckoutData() {
        
        switch (databaseRef){
            case "1":
                diagnosticCheckout();
                break;
            case "2":
                pathologyCheckout();
                break;
            case "3":
                surgicalCheckout();
                break;
        }
    }


    private void diagnosticCheckout() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setUser(Integer.parseInt(DataManager.getInstance().getUserData(getApplicationContext()).data.id));
        checkoutModel.setName(DataManager.getInstance().getUserData(getApplicationContext()).data.name);
        checkoutModel.setMobile(userMobile.getText().toString());
        checkoutModel.setAddress(userAddress.getText().toString());
        checkoutModel.setCheckouts(newDataList);

        Call<ProductConfirmation> call = apiInterface.sendCheckoutDiagnostic(Constant.AUTH,checkoutModel);
        call.enqueue(new Callback<ProductConfirmation>() {
            @Override
            public void onResponse(@NonNull Call<ProductConfirmation> call, @NonNull Response<ProductConfirmation> response) {
                try {
                    DataManager.getInstance().hideProgressMessage();
                    ProductConfirmation productConfirmation = response.body();
                    if (productConfirmation.response==200){
                        Toast.makeText(getApplicationContext(),productConfirmation.message,Toast.LENGTH_SHORT).show();
                        db.diagnosticServiceDao().deleteByIdList(productId);
                        confirmAlert(CheckoutInformation.this,productConfirmation.data.order.orderNo );
                    }else{
                        Toast.makeText(getApplicationContext(),productConfirmation.message,Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductConfirmation> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();

            }
        });

    }
    private void pathologyCheckout() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setUser(Integer.parseInt(DataManager.getInstance().getUserData(getApplicationContext()).data.id));
        checkoutModel.setName(DataManager.getInstance().getUserData(getApplicationContext()).data.name);
        checkoutModel.setMobile(userMobile.getText().toString());
        checkoutModel.setAddress(userAddress.getText().toString());
        checkoutModel.setCheckouts(newDataList);

        Call<ProductConfirmation> call = apiInterface.sendCheckoutPathology(Constant.AUTH,checkoutModel);
        call.enqueue(new Callback<ProductConfirmation>() {
            @Override
            public void onResponse(@NonNull Call<ProductConfirmation> call, @NonNull Response<ProductConfirmation> response) {
                try {
                    DataManager.getInstance().hideProgressMessage();
                    ProductConfirmation productConfirmation = response.body();
                    if (productConfirmation.response==200){
                        Toast.makeText(getApplicationContext(),productConfirmation.message,Toast.LENGTH_SHORT).show();
                        db.pathologyServicesDao().deleteByIdList(productId);
                        confirmAlert(CheckoutInformation.this,productConfirmation.data.order.orderNo );
                    }else {
                        Toast.makeText(getApplicationContext(),productConfirmation.message,Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductConfirmation> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();

            }
        });
    }
    private void surgicalCheckout() {
        DataManager.getInstance().showProgressMessage(this,getString(R.string.please_wait));
        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setUser(Integer.parseInt(DataManager.getInstance().getUserData(getApplicationContext()).data.id));
        checkoutModel.setName(DataManager.getInstance().getUserData(getApplicationContext()).data.name);
        checkoutModel.setMobile(userMobile.getText().toString());
        checkoutModel.setAddress(userAddress.getText().toString());
        checkoutModel.setCheckouts(newDataList);

        Call<ProductConfirmation> call = apiInterface.sendCheckoutSurgical(Constant.AUTH,checkoutModel);
        call.enqueue(new Callback<ProductConfirmation>() {
            @Override
            public void onResponse(@NonNull Call<ProductConfirmation> call, @NonNull Response<ProductConfirmation> response) {
                try {
                    DataManager.getInstance().hideProgressMessage();
                    ProductConfirmation productConfirmation = response.body();
                    if (productConfirmation.response==200){
                         Toast.makeText(getApplicationContext(),productConfirmation.message,Toast.LENGTH_SHORT).show();
                        db.surgicalServiceDao().deleteByIdList(productId);
                        confirmAlert(CheckoutInformation.this,productConfirmation.data.order.orderNo );
                    }else{
                        Toast.makeText(getApplicationContext(),productConfirmation.message,Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductConfirmation> call, @NonNull Throwable t) {
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



            switch (databaseRef){
                case "1":
                    if (diagnosticServiceList.size()==1){
                        orderItem.setText(diagnosticServiceList.get(0).getItem_title());
                        orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                        summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                        summaryDeliveryPrice.setText(getString(R.string.moneySymbol)+" 20");
                        summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
                    }else if (diagnosticServiceList.size() > 1){
                        orderItem.setText("Multiple item");
                        orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                        summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                        summaryDeliveryPrice.setText(getString(R.string.moneySymbol)+" 20");
                        summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
                    }
                    break;
                case "2":
                    if (pathologyServicesList.size()==1){
                        orderItem.setText(pathologyServicesList.get(0).getItem_title());
                        orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                        summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                        summaryDeliveryPrice.setText(getString(R.string.moneySymbol)+" 20");
                        summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
                    }else if (pathologyServicesList.size() > 1){
                        orderItem.setText("Multiple item");
                        orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                        summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                        summaryDeliveryPrice.setText(getString(R.string.moneySymbol)+" 20");
                        summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
                    }
                    break;

                case "3":
                    if (surgicalServiceList.size()==1){
                        orderItem.setText(surgicalServiceList.get(0).getItem_title());
                        orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                        summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                        summaryDeliveryPrice.setText(getString(R.string.moneySymbol)+" 20");
                        summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
                    }else if (surgicalServiceList.size() > 1){
                        orderItem.setText("Multiple item");
                        orderTotalPrice.setText(getString(R.string.moneySymbol)+" "+price);
                        summarySubtotal.setText(getString(R.string.moneySymbol)+" "+price);
                        summaryDeliveryPrice.setText(getString(R.string.moneySymbol)+" 20");
                        summaryTotalPrice.setText(getString(R.string.moneySymbol)+" "+(price+20));
                    }
                    break;
            }
        }

    }



    public void alertLogout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CheckoutInformation.this);
        alertDialog.setTitle(R.string.order_con_msg);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sendCheckoutData();
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }


    public void confirmAlert(Activity activity,String ordreId){
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
        orderNo.setText("Your order no. "+ordreId);
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