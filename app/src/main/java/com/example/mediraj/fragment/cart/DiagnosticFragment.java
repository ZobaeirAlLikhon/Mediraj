package com.example.mediraj.fragment.cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.fragadapter.DfAdapter;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.DiagnosticService;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class DiagnosticFragment extends Fragment {

    private View view;
    private RecyclerView cartRec;
    private TextView noData;
    private ExtendedFloatingActionButton checkoutBtn;
    AppDatabase db;
    List<DiagnosticService> dataList;
    DfAdapter dfAdapter;
    public DiagnosticFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_diagnostic, container, false);
        db = AppDatabase.getDbInstance(getContext());
        checkoutBtn = view.findViewById(R.id.checkoutBtn);
        noData = view.findViewById(R.id.noData);
        cartRec = view.findViewById(R.id.cartRec);
        cartRec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cartRec.setHasFixedSize(true);

        getItemList();
        return view;
    }

    private void getItemList() {
        dataList = db.diagnosticServiceDao().getAll();
        if (dataList.size()>=1){
            noData.setVisibility(View.GONE);
            cartRec.setVisibility(View.VISIBLE);
            checkoutBtn.setVisibility(View.VISIBLE);
            dfAdapter = new DfAdapter(getContext(), dataList);
            cartRec.setAdapter(dfAdapter);
        }else {
            cartRec.setVisibility(View.GONE);
            checkoutBtn.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
    }

    //swipe to delete functionality implement here
}