package com.example.mediraj.adaptar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.R;
import com.example.mediraj.activity.ClinicBookingActivity;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.ClinicalModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClinicServicesAD extends RecyclerView.Adapter<ClinicServicesAD.MyViewHolder> {
    Context context;
    List<ClinicalModel.Datum> clinicalModelList;

    public ClinicServicesAD(Context context, List<ClinicalModel.Datum> clinicalModelList) {
        this.context = context;
        this.clinicalModelList = clinicalModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ClinicServicesAD.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ClinicServicesAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_clinice,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClinicServicesAD.MyViewHolder holder, int position) {
        holder.clinic_name.setText(clinicalModelList.get(position).getTitle());
        holder.address.setText(clinicalModelList.get(position).getAddress());
        Glide.with(context).load(Constant.Clinic_AVATAR_URL +clinicalModelList.get(position).getLogo())
                .apply(new RequestOptions().placeholder(R.drawable.ic_patient))
                .into(holder.circleImageView);
        holder.booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ClinicBookingActivity.class);
                intent.putExtra("hospital_name",clinicalModelList.get(position).getTitle());
                intent.putExtra("hospital_address",clinicalModelList.get(position).getAddress());
                intent.putExtra("clinic_ID",clinicalModelList.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clinicalModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView clinic_name,address;
        ImageView loveReact;
        Button booking;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.cirIM_dia);
            clinic_name=itemView.findViewById(R.id.Tv_clinicname);
            address=itemView.findViewById(R.id.Tv_chekupname);
            loveReact=itemView.findViewById(R.id.btn_img);
            booking=itemView.findViewById(R.id.addToCart_btn);



        }
    }
}
