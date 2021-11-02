package com.example.mediraj.fragment.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.history.HistoryAdapter;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.helper.DataManager;
import com.example.mediraj.model.MedicineOrderModel;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicineOrderFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    MedicineOrderModel model;
    ApiInterface apiInterface;
    TextView noData;

    public MedicineOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medicine_order, container, false);
        apiInterface = APiClient.getClient().create(ApiInterface.class);
        recyclerView = view.findViewById(R.id.recView);
        noData = view.findViewById(R.id.noData);
        DataManager.getInstance().showProgressMessage(getActivity(),getString(R.string.please_wait));
        Call<MedicineOrderModel> call = apiInterface.getMedicineOrderHistory(Constant.AUTH, DataManager.getInstance().getUserData(getContext()).data.id);
        call.enqueue(new Callback<MedicineOrderModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicineOrderModel> call, @NonNull Response<MedicineOrderModel> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    model = response.body();
                    if (model.response==200){
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        historyAdapter = new HistoryAdapter(getContext(),model.data);
                        recyclerView.setAdapter(historyAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    }else{
                       recyclerView.setVisibility(View.GONE);
                       noData.setVisibility(View.VISIBLE);
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<MedicineOrderModel> call, @NonNull Throwable t) {
                DataManager.getInstance().hideProgressMessage();
                call.cancel();
            }
        });
        return view;
    }
}