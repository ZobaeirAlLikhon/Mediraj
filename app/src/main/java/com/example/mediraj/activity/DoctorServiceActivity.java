package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.DocServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class DoctorServiceActivity extends AppCompatActivity {
    RecyclerView rcv;
    List<String> text;
    List<Integer> image;
    DocServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_service);
        rcv = findViewById(R.id.recycler2);

        text = new ArrayList<>();
        image = new ArrayList<>();

        text.add("Doctors appointment");
        text.add("Doctors appointment");
        text.add("Doctors appointment");
        text.add("Doctors appointment");
        text.add("Doctors appointment");
        text.add("Doctors appointment");
        text.add("Doctors appointment");
        text.add("Doctors appointment");

        image.add(R.drawable.ic_appoint);
        image.add(R.drawable.ic_appoint);
        image.add(R.drawable.ic_appoint);
        image.add(R.drawable.ic_appoint);
        image.add(R.drawable.ic_appoint);
        image.add(R.drawable.ic_appoint);
        image.add(R.drawable.ic_appoint);
        image.add(R.drawable.ic_appoint);

        rcv.setLayoutManager(new GridLayoutManager(getApplication(),2));
        adapter = new  DocServiceAdapter(getApplication(),text,image);
        rcv.setAdapter(adapter);



    }
}