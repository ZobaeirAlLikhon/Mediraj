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
import com.example.mediraj.model.AllDiagonosticModel;
import com.example.mediraj.model.AllPathologyModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_diagonesticServicesAD extends RecyclerView.Adapter<Get_diagonesticServicesAD.MyViewHoldr> {
    Context context;
    List<AllDiagonosticModel.Datum> allDiagonosticModels;

    public Get_diagonesticServicesAD(Context context, List<AllDiagonosticModel.Datum> allDiagonosticModels) {
        this.context = context;
        this.allDiagonosticModels = allDiagonosticModels;
    }


    @NonNull
    @NotNull
    @Override
    public MyViewHoldr onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Get_diagonesticServicesAD.MyViewHoldr(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_diagnostic_service_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHoldr holder, int position) {
        holder.diagonstic__name.setText(allDiagonosticModels.get(position).getTitle());
        holder.diagonstic__price.setText(String.valueOf(allDiagonosticModels.get(position).getPrice()));
        Glide.with(context).load(Constant.Diagonestic_AVATAR_URL +allDiagonosticModels.get(position).getLogo()).into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return allDiagonosticModels.size();
    }

    public class MyViewHoldr extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView diagonstic__name,diagonstic__price;
        Button addToCart_btn;
        ImageView diagonstic_love;
        public MyViewHoldr(@NonNull @NotNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.cirIM_dia);
            diagonstic__name=itemView.findViewById(R.id.diagonstic_name);
            diagonstic__price=itemView.findViewById(R.id.diagonstic_price);
            addToCart_btn=itemView.findViewById(R.id.addToCart_btn);
            diagonstic_love=itemView.findViewById(R.id.diagonstic_love);
        }
    }
}
