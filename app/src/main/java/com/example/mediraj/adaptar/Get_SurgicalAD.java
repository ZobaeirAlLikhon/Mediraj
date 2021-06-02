package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllSurgicalModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_SurgicalAD extends RecyclerView.Adapter<Get_SurgicalAD.MyViewHolder> {

    Context context;
    List<AllSurgicalModel.Datum> allSurgicalModels;

    public Get_SurgicalAD(Context context, List<AllSurgicalModel.Datum> allSurgicalModels) {
        this.context=context;
        this.allSurgicalModels = allSurgicalModels;
    }

    @NonNull
    @NotNull
    @Override
    public Get_SurgicalAD.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Get_SurgicalAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_surgical,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Get_SurgicalAD.MyViewHolder holder, int position) {
        holder.surgical_name.setText(allSurgicalModels.get(position).getTitle());
        holder.surgical_price.setText(String.valueOf(allSurgicalModels.get(position).getPrice()));
        Glide.with(context).load(Constant.SURGICAL_AVATAR_URL +allSurgicalModels.get(position).getLogo()).into(holder.cirIM_surgical);

    }

    @Override
    public int getItemCount() {
        return allSurgicalModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cirIM_surgical;
        TextView surgical_name,surgical_price;
        Button surgical_Cart_btn;
        ImageView surgical_love;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cirIM_surgical=itemView.findViewById(R.id.cirIM_surgical);
            surgical_name=itemView.findViewById(R.id.surgical_name);
            surgical_price=itemView.findViewById(R.id.surgical_price);
            surgical_Cart_btn=itemView.findViewById(R.id.surgical_Cart_btn);
            surgical_love=itemView.findViewById(R.id.surgical_love);

        }
    }
}
