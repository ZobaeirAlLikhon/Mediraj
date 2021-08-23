package com.example.mediraj.adaptar;

import android.content.Context;
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
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllDoctorList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAD extends RecyclerView.Adapter<DoctorListAD.MyViewHolder> {

    Context context;
    List<AllDoctorList.Datum> allDoctorListModels;
    OnDocListClick onDocListClick;

    public DoctorListAD(Context context, List<AllDoctorList.Datum> allDoctorListModels, OnDocListClick onDocListClick) {
        this.context = context;
        this.allDoctorListModels = allDoctorListModels;
        this.onDocListClick = onDocListClick;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new DoctorListAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_doctorlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {


        String workPlace = allDoctorListModels.get(position).getDesignation()+", "+ allDoctorListModels.get(position).getOrganization();
        holder.docNameTV.setText(allDoctorListModels.get(position).getFullName());
        holder.descriptionTV.setText(String.valueOf(allDoctorListModels.get(position).getDegree()));
        holder.place.setText(workPlace);
        Glide.with(context)
                .load(Constant.DOCTOR_AVATAR_URL+ allDoctorListModels.get(position).getAvatar())
                .apply(new RequestOptions().placeholder(R.drawable.ic_profile))
                .into(holder.doctorImg);

    }

    @Override
    public int getItemCount() { return allDoctorListModels.size(); }

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
                    onDocListClick.sendData(allDoctorListModels.get(getAdapterPosition()).getId());
                }
            });

        }
    }

    public interface OnDocListClick{
        void sendData(int id);
    }
}
