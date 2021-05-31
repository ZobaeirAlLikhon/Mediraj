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
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mediraj.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MedicineService extends AppCompatActivity {

    private MaterialButton send_btn, call_btn;
    private TextView camera_btn;
    private TextInputLayout textInputLayout,textInputLayout1,textInputLayout2;
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_service);

        //initialize button with id
        send_btn = findViewById(R.id.sendbtnmed);
        call_btn = findViewById(R.id.callbtnmed);

        //camera text initialization
        camera_btn = findViewById(R.id.camera_btn);

        //Material TextInputlayout initialization
        textInputLayout = findViewById(R.id.medbox);
        textInputLayout1 = findViewById(R.id.mc_useraddress);
        textInputLayout2 = findViewById(R.id.mc_phone);

        //initialize image view
        imageView = findViewById(R.id.imgpres);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);


        }

    }
}