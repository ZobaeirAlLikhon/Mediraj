package com.example.mediraj.adaptar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.R;
import com.example.mediraj.activity.DoctorDetailsActivity;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.SingleDepartment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {

    Context context;
    List<SingleDepartment.Data.Doctor> doctorList;


    public DoctorListAdapter(Context context, List<SingleDepartment.Data.Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new DoctorListAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_doctorlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {


        String workPlace = doctorList.get(position).designation+", "+ doctorList.get(position).organization;
        holder.docNameTV.setText(doctorList.get(position).fullName);
        holder.descriptionTV.setText(String.valueOf(doctorList.get(position).degree));
        holder.place.setText(workPlace);
        Glide.with(context)
                .load(Constant.DOCTOR_AVATAR_URL+ doctorList.get(position).avatar)
                .apply(new RequestOptions().placeholder(R.drawable.ic_profile))
                .into(holder.doctorImg);
    }

    @Override
    public int getItemCount() { return doctorList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView doctorImg;
        TextView docNameTV,descriptionTV,place;
        AppCompatButton detailBtn;
        public MyViewHolder( View itemView) {
            super(itemView);

            doctorImg =itemView.findViewById(R.id.circleview_doctor);
            docNameTV=itemView.findViewById(R.id.docNameTV);
            descriptionTV=itemView.findViewById(R.id.descriptionTV);
            place = itemView.findViewById(R.id.place);
            detailBtn = itemView.findViewById(R.id.detailBtn);

            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   context.startActivity(new Intent(context, DoctorDetailsActivity.class)
                           .putExtra("docId",String.valueOf(doctorList.get(getAdapterPosition()).id)));
                }
            });

        }
    }


}
