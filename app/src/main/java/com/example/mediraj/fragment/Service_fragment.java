package com.example.mediraj.fragment;

import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.Service_Adapter;
import com.example.mediraj.adaptar.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.GridLayoutManager.*;


public class Service_fragment extends Fragment {
    RecyclerView servicelist;
    List<String> titles;
    List<Integer> images;
    Service_Adapter adapter;

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
        servicelist = view.findViewById(R.id.servicelist);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");
        titles.add("Doctors appointment");

        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.ic__appoint);
        adapter = new Service_Adapter(getActivity(),titles,images);
        servicelist.setLayoutManager(new GridLayoutManager(getContext(),2));
        servicelist.setAdapter(adapter);

        return view;
    }
}