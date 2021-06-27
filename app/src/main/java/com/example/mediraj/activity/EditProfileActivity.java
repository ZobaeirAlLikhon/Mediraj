package com.example.mediraj.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.BuildConfig;
import com.example.mediraj.R;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.helper.SessionManager;
import com.example.mediraj.model.UserData;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView toolbarText;
    private ImageView toolbarBtn;
    private TextInputEditText userPhone, userName, userEmail, userAddress, edt_date;
    private DatePickerDialog picker;
    private RadioGroup genderGroup;
    private MaterialRadioButton male, female, other;
    private CircleImageView userImg;
    private RelativeLayout profileLay;
    private AppCompatButton saveBtn;
    private double userLat, userLong;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String str_image_path = "",userGender;
    private static final String TAG = EditProfileActivity.class.getName();
    private static final int MY_PERMISSION_CONSTANT = 105;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    File output = null;
    private Uri uri;
    private ApiInterface apiInterface;
    private ImageView fetchAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(EditProfileActivity.this);
        initView();
        try {
            setUserData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        edt_date = findViewById(R.id.userDob);
        userPhone = findViewById(R.id.userPhone);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userAddress = findViewById(R.id.userAddress);
        userImg = findViewById(R.id.userImg);
        profileLay = findViewById(R.id.profilePhoto);
        saveBtn = findViewById(R.id.saveBtn);
        genderGroup = findViewById(R.id.groupGender);
        male = findViewById(R.id.checkMale);
        female = findViewById(R.id.checkFemale);
        other = findViewById(R.id.checkOther);
        fetchAddress = findViewById(R.id.fetchAddress);
        //toolbar
        toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText("Edit Profile");
        toolbarBtn = findViewById(R.id.toolbarBtn);
        //listener
        toolbarBtn.setOnClickListener(this);
        edt_date.setOnClickListener(this);
        profileLay.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        fetchAddress.setOnClickListener(this);
        //other
        apiInterface = APiClient.getClient().create(ApiInterface.class);

        //check location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissionForLocation();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.toolbarBtn) {
            finish();
        } else if (id == R.id.userDob) {
            datePicker();
        } else if (id == R.id.profilePhoto) {
            choosePicture();
        } else if (id == R.id.saveBtn) {
            validation();
        } else if (id == R.id.fetchAddress) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkPermissionForLocation();
            } else {
                getLocation();
            }
        }
    }

    private void validation() {
        int checkedRadioButtonId = genderGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.checkMale) {
            userGender = "Male";
        } else if (checkedRadioButtonId == R.id.checkFemale) {
            userGender = "Female";
        } else if (checkedRadioButtonId == R.id.checkOther) {
            userGender = "Other";
        }


        if (userName.getText().toString().isEmpty()) {
            userName.setError(getString(R.string.userName_error));
            userName.requestFocus();
        } else {
            if (ConnectionManager.connection(this)) {
                saveUserData();
            } else {
                Toast.makeText(this, getString(R.string.internet_connect_msg), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void saveUserData() {
        DataManager.getInstance().showProgressMessage(EditProfileActivity.this, "Please wait...");
        MultipartBody.Part filePart;
        if (!str_image_path.equalsIgnoreCase("")) {
            File file = new File(str_image_path);
            filePart = MultipartBody.Part.createFormData("avatar", file.getName(), RequestBody.create(file, MediaType.parse("image/*")));
        }
        else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));
            filePart = MultipartBody.Part.createFormData("avatar", "image", attachmentEmpty);
        }

        Map<String, RequestBody> mapData = new HashMap<>();

        RequestBody userId = RequestBody.create(DataManager.getInstance().getUserData(EditProfileActivity.this).data.id, MediaType.parse("text/plain"));
        mapData.put("id", userId);
        if (!userName.getText().toString().isEmpty()) {
            RequestBody fullName = RequestBody.create(userName.getText().toString(), MediaType.parse("text/plain"));
            mapData.put("name", fullName);
        }

        if (!userAddress.getText().toString().isEmpty()) {
            RequestBody address = RequestBody.create(userAddress.getText().toString(), MediaType.parse("text/plain"));
            mapData.put("address", address);
        }

        if (!userEmail.getText().toString().isEmpty()) {
            RequestBody email = RequestBody.create(userEmail.getText().toString(), MediaType.parse("text/plain"));
            mapData.put("email", email);
        }

        if (!edt_date.getText().toString().isEmpty()) {
            RequestBody birthDay = RequestBody.create(edt_date.getText().toString(), MediaType.parse("text/plain"));
            mapData.put("dob", birthDay);
        }

        if (userGender != null ) {
            RequestBody gender = RequestBody.create(userGender, MediaType.parse("text/plain"));
            mapData.put("gender", gender);
        }

        Call<UserData> profileUpdateCall = apiInterface.updateProfile(Constant.AUTH,mapData,filePart);
        profileUpdateCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    UserData userData = response.body();
                    if (userData.response==200){
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        SessionManager.writeString(EditProfileActivity.this, Constant.USER_INFO, dataResponse);
                        Toast.makeText(EditProfileActivity.this, userData.message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfileActivity.this,ProfileActivity.class));
                        finish();
                    }else {
                        Toast.makeText(EditProfileActivity.this, userData.message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    //set user data
    private void setUserData() throws ParseException {
        if (DataManager.getInstance().getUserData(getApplicationContext()) != null
                &&
                DataManager.getInstance().getUserData(getApplicationContext()).data != null
                &&
                DataManager.getInstance().getUserData(getApplicationContext()).data.id != null) {

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.name != null) {
                userName.setText(DataManager.getInstance().getUserData(getApplicationContext()).data.name);
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.email != null) {
                userEmail.setText(DataManager.getInstance().getUserData(getApplicationContext()).data.email);
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.mobile != null) {
                userPhone.setText(DataManager.getInstance().getUserData(getApplicationContext()).data.mobile);
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.address != null) {
                userAddress.setText(DataManager.getInstance().getUserData(getApplicationContext()).data.address);
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.avatar != null) {
                Glide.with(this)
                        .load(Constant.USER_AVATAR_URL + DataManager.getInstance().getUserData(getApplicationContext()).data.avatar)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_profile))
                        .into(userImg);
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.gender != null) {
                if (DataManager.getInstance().getUserData(getApplicationContext()).data.gender.equalsIgnoreCase("Male")) {
                    male.setChecked(true);
                } else if (DataManager.getInstance().getUserData(getApplicationContext()).data.gender.equalsIgnoreCase("Female")) {
                    female.setChecked(true);
                } else if (DataManager.getInstance().getUserData(getApplicationContext()).data.gender.equalsIgnoreCase("Other")) {
                    other.setChecked(true);
                }
            }

            if (DataManager.getInstance().getUserData(getApplicationContext()).data.birthDate != null) {
                String date = formatDateFromApi(DataManager.getInstance().getUserData(getApplicationContext()).data.birthDate);
                edt_date.setText(date);
            }
        }
    }

    //date picker
    private void datePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(EditProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                        try {
                            formatDate(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, year, month, day);
        picker.show();
    }

    //date format
    private void formatDate(String date) throws ParseException {
        DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("d MMM, yyyy");
        Date date1 = originalFormat.parse(date);
        edt_date.setText(targetFormat.format(date1));
    }

    private String formatDateFromApi(String date) throws ParseException {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("d MMM, yyyy");
        Date date1 = originalFormat.parse(date);

        return targetFormat.format(date1);
    }

    //photo related section
    private void choosePicture() {
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
        uri = FileProvider.getUriForFile(EditProfileActivity.this,
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
            Log.e("error",e.toString());
        }
        // Save a file: path for use with ACTION_VIEW intents
        Log.e("data test",image.exists()+" ");
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
                userImg.setImageBitmap(bitmap);
                Log.e("cropped", str_image_path);
            }
        }
    }

    private void openCropImageActivity(String str_image_path) {
        CropImage.activity(Uri.fromFile(new File(str_image_path)))
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(EditProfileActivity.this);
    }


    //location related

    //address generate
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
                        String address = getCompleteAddressString(userLat,userLong);
                        userAddress.setText(address);
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


    //CHECKING FOR GPS STATUS
    public void checkPermissionForLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                ||

                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)

                    ||

                    ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Toast.makeText(EditProfileActivity.this, "You should give the permission!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_CONSTANT);

            } else {
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_CONSTANT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_CONSTANT) {
            if (grantResults.length > 0) {
                boolean fine_location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean coarse_location = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (fine_location && coarse_location) {
                    getLocation();
                } else {
                    Toast.makeText(EditProfileActivity.this, " permission needed to work.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditProfileActivity.this, " permission needed to work.", Toast.LENGTH_SHORT).show();
            }
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
                Log.w(TAG,"My Current loction address"+strReturnedAddress.toString());
            } else {
                Log.w(TAG,"My Current loction address "+"No Address returned!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }


}