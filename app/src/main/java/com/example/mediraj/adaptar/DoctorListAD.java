package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.model.AllDoctorList;
import com.example.mediraj.model.AllPathologyModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAD extends RecyclerView.Adapter<DoctorListAD.MyViewHolder> {

    Context context;
    List<AllDoctorList.Datum> allDoctorlistModels;

    public DoctorListAD(Context context, List<AllDoctorList.Datum> allDoctorlistModels) {
        this.context = context;
        this.allDoctorlistModels = allDoctorlistModels;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new DoctorListAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_doctorlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.docNameTV.setText(allDoctorlistModels.get(position).getFullName());
        holder.descriptionTV.setText(String.valueOf(allDoctorlistModels.get(position).getAboutDoctor()));

    }

    @Override
    public int getItemCount() { return allDoctorlistModels.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleview_doctor;
        TextView docNameTV,descriptionTV;
        public MyViewHolder( View itemView) {
            super(itemView);

            circleview_doctor=itemView.findViewById(R.id.circleview_doctor);
            docNameTV=itemView.findViewById(R.id.docNameTV);
            descriptionTV=itemView.findViewById(R.id.descriptionTV);

        }
    }
}
