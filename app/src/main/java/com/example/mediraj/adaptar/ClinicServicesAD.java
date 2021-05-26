package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
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
        holder.clinic_name.setText(clinicalModelList.get(position).getClinicTitle());

    }

    @Override
    public int getItemCount() {
        return clinicalModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView clinic_name,check_up_purpose,net_amount;
        ImageButton loveReact;
        Button addToCart;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.cirIM_dia);
            clinic_name=itemView.findViewById(R.id.Tv_clinicname);
            check_up_purpose=itemView.findViewById(R.id.Tv_chekupname);
            net_amount=itemView.findViewById(R.id.TV_clincprice);
            loveReact=itemView.findViewById(R.id.btn_img);
            addToCart=itemView.findViewById(R.id.addToCart_btn);

        }
    }
}
