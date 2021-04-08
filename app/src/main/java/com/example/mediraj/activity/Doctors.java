package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.DoctorAdpter;
import com.example.mediraj.adaptar.ServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class Doctors extends AppCompatActivity {

    RecyclerView doc_recyclerView;
    List<String> titles;
    List<Integer> images;
    DoctorAdpter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        doc_recyclerView = findViewById(R.id.doc_recyclerView);
        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Akash");
        titles.add("AKASH");


        images.add(R.drawable.user);
        images.add(R.drawable.user);

       // adapter = new DoctorAdpter(getActivity(),titles,images);
        doc_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doc_recyclerView.setAdapter(adapter);

    }
}