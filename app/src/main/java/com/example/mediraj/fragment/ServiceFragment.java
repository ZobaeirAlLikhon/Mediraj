package com.example.mediraj.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mediraj.R;
import com.example.mediraj.adaptar.ServiceAdapter;
import com.example.mediraj.adaptar.SliderAdapter;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.Department;
import com.example.mediraj.webapi.APiClient;
import com.example.mediraj.webapi.ApiInterface;
import com.google.firebase.iid.FirebaseInstanceId;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceFragment extends Fragment {
    RecyclerView serviceList;
    List<String> titles;
    List<Integer> images;
    List<Department.Datum> getDepartment;
    ServiceAdapter adapter;

    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    private ApiInterface apiInterface;
    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.service_fragment, container, false);

        ///

        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.e("token",refreshedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

//       call api
//        apiInterface= APiClient.getClient().create(ApiInterface.class);
//        Call<Department> call=apiInterface.department(Constant.AUTH);
//        call.enqueue(new Callback<Department>() {
//            @Override
//            public void onResponse(Call<Department> call, Response<Department> response) {
//                getDepartment=response.body().getData();
//
//                Log.e("deptData.......",getDepartment.get(0).getTitle());
//            }
//
//            @Override
//            public void onFailure(Call<Department> call, Throwable t) {
//                Log.e("deptfail.......",t.toString());
//            }
//        });
//        sliderView = view.findViewById(R.id.imageSlider);
        serviceList = view.findViewById(R.id.servicelist);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Doctor appoinment");
        titles.add("Diagnostic Service");
        titles.add("Medicine Service");
        titles.add("Clinic Service");
        titles.add("BloodBank Service");
        titles.add("Home Pathology");
        titles.add("Sergical Kits");
        titles.add("Doctor");

        images.add(R.drawable.doctorapp2);
        images.add(R.drawable.ic__appoint);
        images.add(R.drawable.medicin);
        images.add(R.drawable.clinic);
        images.add(R.drawable.blood);
        images.add(R.drawable.pathology);
        images.add(R.drawable.kits);
        images.add(R.drawable.ic__appoint);

        adapter = new ServiceAdapter(getActivity(),titles,images);
        serviceList.setLayoutManager(new GridLayoutManager(getContext(),2));
        serviceList.setAdapter(adapter);

        return view;
    }
}