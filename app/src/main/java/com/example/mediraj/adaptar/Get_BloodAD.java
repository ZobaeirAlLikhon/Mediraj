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

import com.example.mediraj.R;
import com.example.mediraj.activity.BloodBookingctivity;
import com.example.mediraj.model.AllbloodModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_BloodAD extends RecyclerView.Adapter<Get_BloodAD.MyViewHolder> {
    Context context;
    List<AllbloodModel.Datum> allBloodModels;

    public Get_BloodAD(Context context, List<AllbloodModel.Datum> allbloodModel) {
        this.context = context;
        this.allBloodModels = allbloodModel;
    }

    @NonNull
    @NotNull
    @Override
    public Get_BloodAD.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Get_BloodAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bloodbank,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Get_BloodAD.MyViewHolder holder, int position) {
        holder.bloodname.setText(allBloodModels.get(position).getTitle());

        holder.request_btn_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BloodBookingctivity.class);
                intent.putExtra("groupID",allBloodModels.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return allBloodModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cirIM_blood;
        TextView bloodname;
        Button request_btn_blood;
        ImageView blood_love;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cirIM_blood=itemView.findViewById(R.id.cirIM_blood);
            bloodname=itemView.findViewById(R.id.bloodname);
            request_btn_blood=itemView.findViewById(R.id.request_btn_blood);
            blood_love=itemView.findViewById(R.id.blood_love);
        }
    }


}
