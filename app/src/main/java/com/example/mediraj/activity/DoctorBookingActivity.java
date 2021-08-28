package com.example.mediraj.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.CalAdapter;
import com.example.mediraj.helper.ConnectionManager;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.DateModel;
import com.example.mediraj.model.DoctorBookingModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorBookingActivity extends AppCompatActivity implements CalAdapter.OnCalDataClick, View.OnClickListener {
    private final Calendar cal = Calendar.getInstance();
    RecyclerView calRec;
    String docId, name, speciality, designation, workPlace;
    Spinner month;
    int indexOfMonth, indexOfDay, indexOfYear;
    List<DateModel> dateModels = new ArrayList<>();
    CalAdapter.OnCalDataClick onCalDataClick;
    ApiInterface apiInterface;
    int sendDay = 0, sendMonth = 0, sendYear = 0;
    String selectDateFormat = null;
    Button timeBtn;
    String timeStr = null;
    TextInputEditText userName, userPhone, userAddress;
    AppCompatButton bookBtn;
    TextView toolbarText, timeTxt;
    ImageView toolbarBtn;
    String mobile = null;

    TextView docName, docSpe, docDes, docPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_booking);

        initView();

        //get intent data from previous activity
        Intent intent = getIntent();
        if (intent != null) {
            //intent data
            docId = getIntent().getStringExtra("docId");
            name = getIntent().getStringExtra("docName");
            speciality = getIntent().getStringExtra("docSpe");
            designation = getIntent().getStringExtra("docDes");
            workPlace = getIntent().getStringExtra("place");

            docName.setText(name);
            docSpe.setText(speciality);
            docDes.setText(designation);
            docPlace.setText(workPlace);
        }

        //for date operation
        indexOfMonth = Calendar.getInstance().get(Calendar.MONTH);
        indexOfDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        indexOfYear = Calendar.getInstance().get(Calendar.YEAR);
        month.setSelection(indexOfMonth);


        //item listener for spinner
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position + 1 == indexOfMonth + 1) {
                    getDateList(indexOfYear, position + 1, indexOfDay);

                } else if (indexOfMonth + 1 > position + 1) {
                    getDateList(indexOfYear + 1, position + 1, 1);

                } else {
                    getDateList(indexOfYear, position + 1, 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // show some toast message
            }

        });

    }

    private void initView() {
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        onCalDataClick = this;

        month = findViewById(R.id.month_spinner);
        month.setSelection(indexOfMonth);
        calRec = findViewById(R.id.calRec);
        timeBtn = findViewById(R.id.timePicker);
        userName = findViewById(R.id.patientName);
        userPhone = findViewById(R.id.patientContact);
        userAddress = findViewById(R.id.patientAddress);
        bookBtn = findViewById(R.id.bookBtn);
        toolbarText = findViewById(R.id.toolbarText);
        toolbarBtn = findViewById(R.id.toolbarBtn);
        timeTxt = findViewById(R.id.timeTxt);

        docName = findViewById(R.id.docName);
        docSpe = findViewById(R.id.speciality);
        docDes = findViewById(R.id.desInfo);
        docPlace = findViewById(R.id.workPlace);

        toolbarText.setText(R.string.doctor_appointment);

        timeBtn.setOnClickListener(this);
        toolbarBtn.setOnClickListener(this);
        bookBtn.setOnClickListener(this);

        setUserData();
    }

    private void setUserData() {

        if (DataManager.getInstance().getUserData(this).data != null) {
            userName.setText(DataManager.getInstance().getUserData(this).data.name);
            if (DataManager.getInstance().getUserData(this).data.address != null) {
                userAddress.setText(DataManager.getInstance().getUserData(this).data.address);
            }
            if (DataManager.getInstance().getUserData(this).data.mobile != null) {
                String contact = "+88 " + DataManager.getInstance().getUserData(this).data.mobile.substring(0, 5) + "-" + DataManager.getInstance().getUserData(this).data.mobile.substring(5);
                userPhone.setText(contact);
            }

        }

    }

    public void getDateList(int year, int month, int day) {
        List<String> arraylist = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int i = 1;
        while (i <= daysInMonth) {
            String dateFormat = year + "-" + month + "-" + i;
            SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd-EEE", Locale.ENGLISH);
            try {
                String reformattedStr = toFormat.format(Objects.requireNonNull(fromUser.parse(dateFormat)));
                arraylist.add(reformattedStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            i++;
        }
        dateModels.clear();
        for (int j = 0; j < arraylist.size(); j++) {
            dateModels.add(new DateModel(arraylist.get(j).split("-")[3], arraylist.get(j).split("-")[2], arraylist.get(j).split("-")[1], arraylist.get(j).split("-")[0], false));

        }
        calRec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        calRec.setAdapter(new CalAdapter(this, dateModels, onCalDataClick));

    }

    @Override
    public void sendData(int day, int month, int year) {
        sendDay = day;
        sendMonth = month;
        sendYear = year;

        if (sendDay == 0 || sendMonth == 0 || sendYear == 0) {
            selectDateFormat = null;
        } else if (sendDay < indexOfDay && sendMonth == (indexOfMonth + 1)) {
            if (sendYear == indexOfYear) {
                selectDateFormat = null;
                Toast.makeText(DoctorBookingActivity.this, "Select date from " + indexOfDay + " or above.", Toast.LENGTH_SHORT).show();
            }
        } else if (sendMonth < (indexOfMonth + 1)) {
            selectDateFormat = sendYear + "-" + sendMonth + "-" + sendDay;
            Toast.makeText(DoctorBookingActivity.this, "Your selected date is " + sendDay + "-" + sendMonth + "-" + sendYear, Toast.LENGTH_SHORT).show();
        } else {
            selectDateFormat = sendYear + "-" + sendMonth + "-" + sendDay;
            Toast.makeText(DoctorBookingActivity.this, "Your selected date is " + sendDay + "-" + sendMonth + "-" + sendYear, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.timePicker) {
            openTimePicker();
        } else if (id == R.id.bookBtn) {
            if (ConnectionManager.connection(this)) {
                getDoctorAppointment();
            } else {
                Toast.makeText(this, R.string.internet_connect_msg, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.toolbarBtn) {
            finish();
        }
    }

    private void getDoctorAppointment() {
        try {
            if (!userPhone.getText().toString().equals(" ") && userPhone.getText().length() == 16) {
                String raw_phone = userPhone.getText().toString().split(" ")[1];
                mobile = raw_phone.split("-")[0] + raw_phone.split("-")[1];
            } else {
                userPhone.setError(getString(R.string.userPhone_error_valid));
                userPhone.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userName.getText().toString().isEmpty()) {
            userName.setError(getString(R.string.userName_error));
            userName.requestFocus();
        } else if (userPhone.getText().toString().isEmpty()) {
            userPhone.setError(getString(R.string.userPhone_error));
            userPhone.requestFocus();
        } else if (mobile == null) {
            userPhone.setError(getString(R.string.userPhone_error_valid));
            userPhone.requestFocus();
        } else if (mobile.length() != 11) {
            userPhone.setError(getString(R.string.userPhone_error_valid));
            userPhone.requestFocus();
        } else if (!mobile.startsWith("0")) {
            userPhone.setError(getString(R.string.userPhone_error_number));
            userPhone.requestFocus();
        } else if (userAddress.getText().toString().isEmpty()) {
            userAddress.setError(getString(R.string.please_enter_your_address));
        } else if (selectDateFormat == null || selectDateFormat.equals("0-0-0")) {
            Toast.makeText(this, R.string.enter_date, Toast.LENGTH_SHORT).show();
        } else if (timeTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_time, Toast.LENGTH_SHORT).show();
        } else {
            DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
            Map<String, String> map = new HashMap<>();
            map.put("doctor_id", docId);
            map.put("user_id", DataManager.getInstance().getUserData(this).data.id);
            map.put("name", userName.getText().toString());
            map.put("email", "info.mediRaj@test.com");
            map.put("mobile", userPhone.getText().toString());
            map.put("address", userAddress.getText().toString());
            map.put("schedule", selectDateFormat + " " + timeTxt.getText().toString());

            Log.e("api data", map + " ");

            Call<DoctorBookingModel> call = apiInterface.doctorBooking(Constant.AUTH, map);
            call.enqueue(new Callback<DoctorBookingModel>() {
                @Override
                public void onResponse(@NonNull Call<DoctorBookingModel> call, @NonNull Response<DoctorBookingModel> response) {
                    DataManager.getInstance().hideProgressMessage();
                    DoctorBookingModel doctorBookingModel = response.body();
                    assert doctorBookingModel != null;
                    if (doctorBookingModel.response == 200) {
                        Toast.makeText(DoctorBookingActivity.this, doctorBookingModel.message, Toast.LENGTH_SHORT).show();
                        confirmAlert(DoctorBookingActivity.this);

                    } else {
                        Toast.makeText(DoctorBookingActivity.this, doctorBookingModel.message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DoctorBookingModel> call, @NonNull Throwable t) {
                    DataManager.getInstance().hideProgressMessage();
                    call.cancel();
                }
            });

        }


    }

    private void openTimePicker() {
        //time picker
        boolean isSystem24Hour = DateFormat.is24HourFormat(this);
        int timeIs12Or24 = isSystem24Hour ? TimeFormat.CLOCK_24H : TimeFormat.CLOCK_12H;
        MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder();
        MaterialTimePicker materialTimePicker = builder
                .setTitleText("Appointment Time")
                .setTimeFormat(timeIs12Or24)
                .setHour(cal.get(Calendar.HOUR_OF_DAY))
                .setMinute(cal.get(Calendar.MINUTE))
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .build();
        materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStr = ((materialTimePicker.getHour() > 12) ? materialTimePicker.getHour() % 12 : materialTimePicker.getHour()) + ":"
                        + (materialTimePicker.getMinute() < 10 ? ("0" + materialTimePicker.getMinute()) : materialTimePicker.getMinute()) + " "
                        + ((materialTimePicker.getHour() >= 12) ? "PM" : "AM");

                timeTxt.setText(timeStr);
            }
        });
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