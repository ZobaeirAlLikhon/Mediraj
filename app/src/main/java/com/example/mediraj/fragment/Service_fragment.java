package com.example.mediraj.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.ServiceAdapter;
import com.example.mediraj.adaptar.SliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class Service_fragment extends Fragment {
    RecyclerView serviceList;
    List<String> titles;
    List<Integer> images;
    ServiceAdapter adapter;

    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    public Service_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_fragment, container, false);
//        sliderView = view.findViewById(R.id.imageSlider);
        serviceList = view.findViewById(R.id.servicelist);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment Doctors appointment");

        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        adapter = new ServiceAdapter(getActivity(),titles,images);
        serviceList.setLayoutManager(new GridLayoutManager(getContext(),2));
        serviceList.setAdapter(adapter);

        return view;
    }
}