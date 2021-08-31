package com.example.mediraj.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.mediraj.BuildConfig;
import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.MedicinRequestModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.santalu.maskara.widget.MaskEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MedicineService extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int MY_PERMISSION_CONSTANT = 3;
    private static final String TAG = MedicineService.class.getName();
    ApiInterface apiInterface;
    File output = null;
    Double userLat = null, userLong = null;
    private MaterialButton send_btn, call_btn;
    private TextView toolbarTxt, uploadText;
    private CardView camera_btn;
    private TextInputEditText medName, userLocation;
    private MaskEditText userCell;
    private ImageView imageView, fetchAddress, ivBack;
    private Uri uri;
    private String str_image_path = "", phone = null;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_service);
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MedicineService.this);
        initView();

    }

    private void initView() {
        //initialize button with id
        send_btn = findViewById(R.id.sendBtn);
        call_btn = findViewById(R.id.callBtn);
        //camera text initialization
        camera_btn = findViewById(R.id.cameraBtn);

        //Material Text Input edit text initialization
        medName = findViewById(R.id.medName);
        userLocation = findViewById(R.id.userLocation);
        userCell = findViewById(R.id.userCell);

        //initialize image view
        imageView = findViewById(R.id.imgpres);
        fetchAddress = findViewById(R.id.fetchAddress);
        uploadText = findViewById(R.id.imgAdd);
        ivBack = findViewById(R.id.toolbarBtn);
        toolbarTxt = findViewById(R.id.toolbarText);

        camera_btn.setOnClickListener(this);
        call_btn.setOnClickListener(this);
        send_btn.setOnClickListener(this);
        fetchAddress.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        toolbarTxt.setText(getString(R.string.medi_service));
        setUserData();
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.cameraBtn) {
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            showImageSelection();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            Toast.makeText(getApplicationContext(), "Need Permission to Work!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        } else if (id == R.id.callBtn) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:01740155577"));
            startActivity(intent);
        } else if (id == R.id.sendBtn) {
            validateData();
        } else if (id == R.id.fetchAddress) {
            //check location permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkPermissionForLocation();
            } else {
                getLocation();
            }
        } else if (id == R.id.toolbarBtn) {
            finish();
        }

    }

    private void validateData() {

        try {
            if (!userCell.getText().toString().equals(" ") && userCell.getText().length() == 16) {
                String raw_phone = userCell.getText().toString().split(" ")[1];
                phone = raw_phone.split("-")[0] + raw_phone.split("-")[1];
            } else {
                userCell.setError(getString(R.string.userPhone_error_valid));
                userCell.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (userCell.getText().toString().isEmpty()) {
            userCell.setError("Please enter mobile number.");
            userCell.requestFocus();
        } else if (phone == null) {
            userCell.setError("Please enter valid mobile number.");
            userCell.requestFocus();
        } else if (!phone.startsWith("0")) {
            userCell.setError("Please enter valid mobile number.");
            userCell.requestFocus();
        } else if (phone.length() != 11) {
            userCell.setError("Enter your 11 digits phone number.");
            userCell.requestFocus();
        } else if (userLocation.getText().toString().isEmpty()) {
            userLocation.setError("Please enter address");
        } else if (str_image_path.equals("") && medName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter medicine name or capture prescription picture", Toast.LENGTH_SHORT).show();
        } else {
            if (ConnectionManager.connection(this)) {
                sendData();
            } else {
                Toast.makeText(MedicineService.this, getString(R.string.internet_connect_msg), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void sendData() {
        DataManager.getInstance().showProgressMessage(MedicineService.this, "Please wait...");
        MultipartBody.Part filePart;
        if (!str_image_path.equalsIgnoreCase("")) {
            File file = new File(str_image_path);
            filePart = MultipartBody.Part.createFormData("avatar", file.getName(), RequestBody.create(file, MediaType.parse("image/*")));
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));
            filePart = MultipartBody.Part.createFormData("image", "image", attachmentEmpty);
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("user_id", RequestBody.create(DataManager.getInstance().getUserData(this).data.id, MediaType.parse("text/plain")));
        map.put("name", RequestBody.create(DataManager.getInstance().getUserData(this).data.name, MediaType.parse("text/plain")));
        map.put("mobile", RequestBody.create(userCell.getText().toString(), MediaType.parse("text/plain")));
        map.put("address", RequestBody.create(userLocation.getText().toString(), MediaType.parse("text/plain")));
        Log.e("data", map.toString());

        if (!medName.getText().toString().isEmpty()) {
            map.put("medicine", RequestBody.create(medName.getText().toString(), MediaType.parse("text/plain")));
        }

        Call<MedicinRequestModel> medCall = apiInterface.medicine_services(Constant.AUTH, map, filePart);
        medCall.enqueue(new Callback<MedicinRequestModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicinRequestModel> call, @NonNull Response<MedicinRequestModel> response) {
                DataManager.getInstance().hideProgressMessage();

                try {
                    MedicinRequestModel medicinRequestModel = response.body();
                    if (medicinRequestModel.getResponse() == 200) {
                        Toast.makeText(MedicineService.this, medicinRequestModel.getMessage(), Toast.LENGTH_SHORT).show();
                        confirmAlert(MedicineService.this);
                    } else {
                        Toast.makeText(MedicineService.this, medicinRequestModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MedicinRequestModel> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
            }
        });

    }


    private void setUserData() {
        if (DataManager.getInstance().getUserData(this).data != null) {
            if (DataManager.getInstance().getUserData(this).data.address != null) {
                userLocation.setText(DataManager.getInstance().getUserData(this).data.address);
            } else {
                //check location permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    checkPermissionForLocation();
                } else {
                    getLocation();
                }
            }

            if (DataManager.getInstance().getUserData(this).data.mobile != null) {
                String phoneNumber = "+88 " + DataManager.getInstance().getUserData(this).data.mobile.substring(0, 5) + "-" + DataManager.getInstance().getUserData(this).data.mobile.substring(5);
                userCell.setText(phoneNumber);
            }
        }
    }

    // OPEN SHORTING MENU DIALOG
    public void showImageSelection() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Widget_Material_ListPopupWindow;
        dialog.setContentView(R.layout.dialog_image_selection);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        LinearLayout layoutCamera = dialog.findViewById(R.id.layoutCemera);
        LinearLayout layoutGallery = dialog.findViewById(R.id.layoutGallary);
        layoutCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
                try {
                    openCamera();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        layoutGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
                openGallery();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select a Image"), SELECT_FILE);
    }

    private void openCamera() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        output = createImageFile();
        uri = FileProvider.getUriForFile(MedicineService.this,
                BuildConfig.APPLICATION_ID + ".provider",
                output);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private File createImageFile() throws IOException {
        String imageFileName = "cachedImage";

        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    getCacheDir()      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }
        // Save a file: path for use with ACTION_VIEW intents
        Log.e("data test", image.exists() + " ");
        str_image_path = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Log.e("camera activityResult", str_image_path);
                openCropImageActivity(str_image_path);
            } else if (requestCode == SELECT_FILE) {
                CropImage.activity(data.getData()).start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                str_image_path = DataManager.getPathFromUri(this, result.getUri());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(str_image_path, options);
                uploadText.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                Log.e("cropped", str_image_path);
            }
        }
    }

    private void openCropImageActivity(String str_image_path) {
        CropImage.activity(Uri.fromFile(new File(str_image_path)))
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(MedicineService.this);
    }


    //CHECKING FOR GPS STATUS
    public void checkPermissionForLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                ||

                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MedicineService.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)

                    ||

                    ActivityCompat.shouldShowRequestPermissionRationale(MedicineService.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Toast.makeText(MedicineService.this, "You should give the permission!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MedicineService.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_CONSTANT);

            } else {
                ActivityCompat.requestPermissions(MedicineService.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_CONSTANT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_CONSTANT) {
            if (grantResults.length > 0) {
                boolean fine_location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean coarse_location = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (fine_location && coarse_location) {
                    getLocation();
                } else {
                    Toast.makeText(MedicineService.this, " permission needed to work.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MedicineService.this, " permission needed to work.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //location related functions
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissionForLocation();
        } else {
            Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        Log.d(TAG, "onSuccess: Location : " + location.toString());
                        Log.d(TAG, "onSuccess: Latitude: " + location.getLatitude());
                        Log.d(TAG, "onSuccess: Longitude: " + location.getLongitude());
                        Log.d(TAG, "onSuccess: Time: " + location.getTime());
                        userLat = location.getLatitude();
                        userLong = location.getLongitude();

                        try {
                            String address = getCompleteAddressString(userLat, userLong);
                            userLocation.setText(address);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            locationTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                }
                strAdd = strReturnedAddress.toString();
                Log.w(TAG, "My Current loction address" + strReturnedAddress.toString());
            } else {
                Log.w(TAG, "My Current location address " + "No Address returned!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    public void confirmAlert(Activity activity) {
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
                activity.startActivity(new Intent(activity.getApplicationContext(), HomeActivity.class));
                activity.finish();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
