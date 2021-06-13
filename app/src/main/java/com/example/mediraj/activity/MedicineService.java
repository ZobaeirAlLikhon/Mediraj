package com.example.mediraj.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.MedicinRequestModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MedicineService extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton send_btn, call_btn;
    private TextView camera_btn;
    private TextInputLayout textInputLayout,textInputLayout1,textInputLayout2;
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 1888;
    String name,mobile,address,medicine,user_id,picturePath;
    Bitmap photoBitmap;
    ApiInterface apiInterface;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_service);
        
        initView();
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_data();
            }
        });

        //call_btn
        call_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:01740155577"));
            startActivity(intent);
        });

        //camera_btn
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1888);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

    }

    private void send_data() {
        apiInterface= APiClient.getClient().create(ApiInterface.class);
        user_id= DataManager.getInstance().getUserData(this).data.id;
        name=DataManager.getInstance().getUserData(this).data.name;
        address=textInputLayout1.getEditText().getText().toString();
        mobile=textInputLayout2.getEditText().getText().toString();
        Log.e("mobile",mobile);
        String medicine=textInputLayout.getEditText().getText().toString();
        Map<String,RequestBody> map=new HashMap<>();
        map.put("user_id",RequestBody.create(user_id,MediaType.parse("text/plain")));
        map.put("name",RequestBody.create(name,MediaType.parse("text/plain")));
        map.put("mobile",RequestBody.create(textInputLayout2.getEditText().getText().toString(),MediaType.parse("text/plain")));
        map.put("address",RequestBody.create(address,MediaType.parse("text/plain")));
        map.put("medicine",RequestBody.create(medicine,MediaType.parse("text/plain")));

        File file=new File(picturePath);
        Log.e("picture path",picturePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestBody);

//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("prescription", file.getName(), RequestBody.create(file,MediaType.parse("image/*")));
        Call<MedicinRequestModel> medicinRequestModelCall= apiInterface.medicine_services(Constant.AUTH,map,body);
        medicinRequestModelCall.enqueue(new Callback<MedicinRequestModel>() {
            @Override
            public void onResponse(Call<MedicinRequestModel> call, Response<MedicinRequestModel> response) {
                Log.e("Success.......",response.body().getMessage());
            }

            @Override
            public void onFailure(Call<MedicinRequestModel> call, Throwable t) {
                Log.e("Faild.......",t.toString());

            }
        });

    }

    private void initView() {
        //initialize button with id
        send_btn = findViewById(R.id.sendbtnmed);
        call_btn = findViewById(R.id.callbtnmed);
        //camera text initialization
        camera_btn = findViewById(R.id.camera_btn);

        //Material TextInputlayout initialization
        textInputLayout = findViewById(R.id.medbox);
        textInputLayout1 = findViewById(R.id.mc_useraddress);
        textInputLayout2 = findViewById(R.id.mc_phone);
        setUserData();

        //initialize image view
        imageView = findViewById(R.id.imgpres);

        camera_btn.setOnClickListener(this);
        call_btn.setOnClickListener(this);
    }

    private void setUserData() {
        textInputLayout1.getEditText().setText(DataManager.getInstance().getUserData(this).data.address);
        textInputLayout2.getEditText().setText("+88 "+DataManager.getInstance().getUserData(this).data.mobile.substring(0,5)+"-"+DataManager.getInstance().getUserData(this).data.mobile.substring(5));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photoBitmap = (Bitmap) data.getExtras().get("data");

            uri=getImageUri(MedicineService.this,photoBitmap);
            picturePath=getRealPathFromURI(uri);
            Log.e("imageUri:---------",uri.toString());
            Log.e("imagePath:---------",picturePath);


            imageView.setImageBitmap(photoBitmap);



        }

    }

    @Override
    public void onClick(View v) {

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }



}